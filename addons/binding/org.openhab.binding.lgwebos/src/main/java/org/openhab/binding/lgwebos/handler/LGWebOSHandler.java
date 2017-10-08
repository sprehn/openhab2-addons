/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.lgwebos.handler;

import static org.openhab.binding.lgwebos.LGWebOSBindingConstants.*;

import java.util.List;
import java.util.Map;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.lgwebos.internal.ChannelHandler;
import org.openhab.binding.lgwebos.internal.LauncherApplication;
import org.openhab.binding.lgwebos.internal.MediaControlPlayer;
import org.openhab.binding.lgwebos.internal.MediaControlStop;
import org.openhab.binding.lgwebos.internal.PowerControlPower;
import org.openhab.binding.lgwebos.internal.TVControlChannel;
import org.openhab.binding.lgwebos.internal.TVControlChannelName;
import org.openhab.binding.lgwebos.internal.TVControlDown;
import org.openhab.binding.lgwebos.internal.TVControlUp;
import org.openhab.binding.lgwebos.internal.ToastControlToast;
import org.openhab.binding.lgwebos.internal.VolumeControlMute;
import org.openhab.binding.lgwebos.internal.VolumeControlVolume;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.device.ConnectableDeviceListener;
import com.connectsdk.discovery.DiscoveryManager;
import com.connectsdk.discovery.DiscoveryManagerListener;
import com.connectsdk.service.DeviceService;
import com.connectsdk.service.DeviceService.PairingType;
import com.connectsdk.service.command.ServiceCommandError;
import com.google.common.collect.ImmutableMap;

/**
 * The {@link LGWebOSHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Sebastian Prehn - Initial contribution
 * @since 1.8.0
 */
public class LGWebOSHandler extends BaseThingHandler implements ConnectableDeviceListener, DiscoveryManagerListener {

    private Logger logger = LoggerFactory.getLogger(LGWebOSHandler.class);
    private DiscoveryManager discoveryManager;

    // ChannelID to CommandHandler Map
    private final Map<String, ChannelHandler> channelHandlers = ImmutableMap.<String, ChannelHandler> builder()
            .put(CHANNEL_VOLUME, new VolumeControlVolume()).put(CHANNEL_POWER, new PowerControlPower())
            .put(CHANNEL_MUTE, new VolumeControlMute()).put(CHANNEL_CHANNEL, new TVControlChannel())
            .put(CHANNEL_CHANNEL_UP, new TVControlUp()).put(CHANNEL_CHANNEL_DOWN, new TVControlDown())
            .put(CHANNEL_CHANNEL_NAME, new TVControlChannelName()).put(CHANNEL_APP_LAUNCHER, new LauncherApplication())
            .put(CHANNEL_MEDIA_STOP, new MediaControlStop()).put(CHANNEL_TOAST, new ToastControlToast())
            .put(CHANNEL_MEDIA_PLAYER, new MediaControlPlayer()).build();

    public LGWebOSHandler(Thing thing, DiscoveryManager discoveryManager) {
        super(thing);
        this.discoveryManager = discoveryManager;
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        logger.debug("handleCommand({},{}) is called", channelUID, command);
        ChannelHandler handler = channelHandlers.get(channelUID.getId());
        if (handler == null) {
            logger.warn(
                    "Unable to handle command {}. No handler found for channel {}. This must not happen. Please report as a bug.",
                    command, channelUID);
            return;
        }
        ConnectableDevice device = getDevice();
        if (device == null) {
            logger.debug("Device {} not found - most likely is is currently offline. Details: Channel {}, Command {}.",
                    getThing().getProperties().get(PROPERTY_IP_ADDRESS), channelUID.getId(), command);
        }
        handler.onReceiveCommand(device, channelUID.getId(), this, command);
    }

    private ConnectableDevice getDevice() {
        String ip = getThing().getProperties().get(PROPERTY_IP_ADDRESS);
        return discoveryManager.getCompatibleDevices().get(ip);
    }

    @Override
    public void initialize() {
        discoveryManager.addListener(this);

        ConnectableDevice device = getDevice();
        if (device == null) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.NONE, "TV is off");
        } else {
            device.addListener(this);
            if (device.isConnected()) {
                updateStatus(ThingStatus.ONLINE, ThingStatusDetail.NONE, "Connected");
            } else {
                updateStatus(ThingStatus.ONLINE, ThingStatusDetail.NONE, "Connecting ...");
                device.connect(); // on success onDeviceReady will be called,
                                  // if pairing is required onPairingRequired,
                                  // otherwise onConnectionFailed
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ConnectableDevice device = getDevice();
        if (device != null) {
            device.removeListener(this);
        }
        discoveryManager.removeListener(this);
    }
    // Connectable Device Listener

    @Override
    public void onDeviceReady(ConnectableDevice device) { // this gets called on connection success
        updateStatus(ThingStatus.ONLINE, ThingStatusDetail.NONE, "Connected");
        refreshAllChannelSubscriptions(device);
        channelHandlers.forEach((k, v) -> v.onDeviceReady(device, k, this));
    }

    @Override
    public void onDeviceDisconnected(ConnectableDevice device) {
        logger.debug("Device disconnected: {}", device);
        for (Map.Entry<String, ChannelHandler> e : channelHandlers.entrySet()) {
            e.getValue().onDeviceRemoved(device, e.getKey(), this);
            e.getValue().removeAnySubscription(device);
        }
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.NONE, "TV is off");
    }

    @Override
    public void onPairingRequired(ConnectableDevice device, DeviceService service, PairingType pairingType) {
        updateStatus(thing.getStatus(), ThingStatusDetail.CONFIGURATION_PENDING, "Pairing Required");
    }

    @Override
    public void onCapabilityUpdated(ConnectableDevice device, List<String> added, List<String> removed) {
        logger.debug("Capabilities updated: {} - added: {} - removed: {}", device, added, removed);
        refreshAllChannelSubscriptions(device);
    }

    @Override
    public void onConnectionFailed(ConnectableDevice device, ServiceCommandError error) {
        logger.debug("Connection failed: {} - error: {}", device, error.getMessage());
        updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.COMMUNICATION_ERROR, "Connection Failed");
    }

    // callback methods for commandHandlers
    public void postUpdate(String channelUID, State state) {
        updateState(channelUID, state);
    }

    public boolean isChannelInUse(String channelId) {
        return isLinked(channelId);
    }

    // channel linking modifications

    @Override
    public void channelLinked(ChannelUID channelUID) {
        refreshChannelSubscription(channelUID);
    }

    @Override
    public void channelUnlinked(ChannelUID channelUID) {
        refreshChannelSubscription(channelUID);
    }

    // private helpers

    /**
     * Refresh channel subscription for one specific channel.
     *
     * @param channelUID must not be <code>null</code>
     */
    private void refreshChannelSubscription(ChannelUID channelUID) {
        String channelId = channelUID.getId();
        ConnectableDevice device = getDevice();

        // may be called even if the device is not currently connected
        if (device != null && device.isConnected()) {
            channelHandlers.get(channelId).refreshSubscription(device, channelId, this);
        }
    }

    /**
     * Refresh channel subscriptions on all handlers.
     *
     * @param device must not be <code>null</code>
     */
    private void refreshAllChannelSubscriptions(ConnectableDevice device) {
        channelHandlers.forEach((k, v) -> v.refreshSubscription(device, k, this));
    }

    // just to make sure, this device is registered, if it was powered off during initialization
    @Override
    public void onDeviceAdded(DiscoveryManager manager, ConnectableDevice device) {
        String ip = getThing().getProperties().get(PROPERTY_IP_ADDRESS);
        if (device.getIpAddress().equals(ip)) {
            device.addListener(this);
            updateStatus(ThingStatus.ONLINE, ThingStatusDetail.NONE, "Device Ready");
            device.connect();
        }
    }

    @Override
    public void onDeviceUpdated(DiscoveryManager manager, ConnectableDevice device) {
        String ip = getThing().getProperties().get(PROPERTY_IP_ADDRESS);
        if (device.getIpAddress().equals(ip)) {
            device.addListener(this);
        }
    }

    @Override
    public void onDeviceRemoved(DiscoveryManager manager, ConnectableDevice device) {
        // NOP
    }

    @Override
    public void onDiscoveryFailed(DiscoveryManager manager, ServiceCommandError error) {
        // NOP
    }
}

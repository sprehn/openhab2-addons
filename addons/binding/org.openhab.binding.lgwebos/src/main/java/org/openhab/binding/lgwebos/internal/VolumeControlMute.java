/**
 * Copyright (c) 2010-2017 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.lgwebos.internal;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.lgwebos.handler.LGWebOSHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.connectsdk.device.ConnectableDevice;
import com.connectsdk.service.capability.VolumeControl;
import com.connectsdk.service.capability.VolumeControl.MuteListener;
import com.connectsdk.service.command.ServiceCommandError;
import com.connectsdk.service.command.ServiceSubscription;

/**
 * Handles TV Control Mute Command.
 *
 * @author Sebastian Prehn
 * @since 1.8.0
 */
public class VolumeControlMute extends BaseChannelHandler<MuteListener> {
    private Logger logger = LoggerFactory.getLogger(VolumeControlMute.class);

    private VolumeControl getControl(final ConnectableDevice device) {
        return device.getCapability(VolumeControl.class);
    }

    @Override
    public void onReceiveCommand(final ConnectableDevice d, String channelId, LGWebOSHandler handler, Command command) {
        if (d == null) {
            return;
        }
        if (d.hasCapabilities(VolumeControl.Mute_Set)) {
            OnOffType onOffType;
            if (command instanceof OnOffType) {
                onOffType = (OnOffType) command;
            } else {
                logger.warn("only accept OnOffType");
                return;
            }
            getControl(d).setMute(OnOffType.ON == onOffType, createDefaultResponseListener());
        }
    }

    @Override
    protected ServiceSubscription<MuteListener> getSubscription(final ConnectableDevice device, final String channelId,
            final LGWebOSHandler handler) {
        if (device.hasCapability(VolumeControl.Mute_Subscribe)) {
            return getControl(device).subscribeMute(new MuteListener() {

                @Override
                public void onError(ServiceCommandError error) {
                    logger.debug("{} {} {}", error.getCode(), error.getPayload(), error.getMessage());
                }

                @Override
                public void onSuccess(Boolean value) {
                    handler.postUpdate(channelId, value ? OnOffType.ON : OnOffType.OFF);
                }
            });
        } else {
            return null;
        }
    }
}

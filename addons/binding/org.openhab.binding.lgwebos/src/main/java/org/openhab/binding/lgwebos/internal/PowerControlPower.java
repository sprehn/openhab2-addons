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
import com.connectsdk.service.capability.PowerControl;

/**
 * Handles Power Control Command.
 * Note: Connect SDK only supports powering OFF for most devices.
 *
 * @author Sebastian Prehn
 * @since 1.8.0
 */
public class PowerControlPower extends BaseChannelHandler<Void> {
    private Logger logger = LoggerFactory.getLogger(PowerControlPower.class);

    private PowerControl getControl(final ConnectableDevice device) {
        return device.getCapability(PowerControl.class);
    }

    @Override
    public void onReceiveCommand(final ConnectableDevice d, String channelId, LGWebOSHandler handler, Command command) {
        if (d == null) {
            /*
             * Unable to send anything to a null device. Unless the user configured autoupdate="false" neither
             * onDeviceReady nor onDeviceRemoved will be called and item state would be permanently inconsistent.
             * Therefore setting state to OFF
             */
            handler.postUpdate(channelId, OnOffType.OFF);
            return;
        }

        OnOffType onOffType;
        if (command instanceof OnOffType) {
            onOffType = (OnOffType) command;
        } else {
            logger.warn("only accept OnOffType");
            return;
        }

        if (OnOffType.ON == onOffType && d.hasCapabilities(PowerControl.On)) {
            getControl(d).powerOn(createDefaultResponseListener());
        } else if (OnOffType.OFF == onOffType && d.hasCapabilities(PowerControl.Off)) {
            getControl(d).powerOff(createDefaultResponseListener());
        }
    }

    @Override
    public void onDeviceReady(ConnectableDevice device, final String channelId, final LGWebOSHandler handler) {
        super.onDeviceReady(device, channelId, handler);
        handler.postUpdate(channelId, OnOffType.ON);
    }

    @Override
    public void onDeviceRemoved(ConnectableDevice device, final String channelId, final LGWebOSHandler handler) {
        super.onDeviceRemoved(device, channelId, handler);
        handler.postUpdate(channelId, OnOffType.OFF);
    }
}

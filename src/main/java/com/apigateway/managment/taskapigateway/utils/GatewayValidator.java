package com.apigateway.managment.taskapigateway.utils;

import com.apigateway.managment.taskapigateway.error.ex.GatewayDataValidationException;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import com.google.common.net.InetAddresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class GatewayValidator {
    @Autowired
    private GatewayRepository gatewayRepository;

    public void validateGatewayData(Gateway gateway) throws GatewayDataValidationException {
        validateGatewayIpAddress(gateway.getIpAddress());
        validateGatewayDevicesNumber(gateway.getPeripheralDevices());
        validateExistGatewayWithSerialNumber(gateway.getSerialNumber());
    }

    public void validateGatewayDevicesNumber(Set<PeripheralDevice> devices) throws GatewayDataValidationException {
        handlingErrorResponse((devices == null || devices.isEmpty()),"The gateway most have at least one peripheral device.");
        handlingErrorResponse((devices.size() > 10), "No more than 10 devices are allowed per gateway.");
    }

    public void validateGatewayIpAddress(String ipAddress) throws GatewayDataValidationException {
        handlingErrorResponse(!isValidIpAddress(ipAddress), String.format("The IP Address for this gateway is invalid: %s.", ipAddress));
        validateExistGatewayWithIpAddress(ipAddress);
    }

    private boolean isValidIpAddress(String ip) {
        return !(ip == null || ip.trim().equals("") || !InetAddresses.isInetAddress(ip));
    }

    public void validateExistGatewayWithIpAddress(String ipAddress) throws GatewayDataValidationException {
        handlingErrorResponse(gatewayRepository.findGatewayByIpAddress(ipAddress).isPresent(), String.format("The IP Address of the gateway already exist: %s.", ipAddress));
    }

    public void validateExistGatewayWithSerialNumber(String serialNumber) throws GatewayDataValidationException {
        handlingErrorResponse(gatewayRepository.findGatewayBySerialNumber(serialNumber).isPresent(), String.format("The serial number of this gateway already exist: %s.", serialNumber));
    }

    private void handlingErrorResponse(boolean condition, String message) throws GatewayDataValidationException {
        if (condition) throw new GatewayDataValidationException(message);
    }
}

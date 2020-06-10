package com.apigateway.managment.taskapigateway.model;

public enum EPeripheralDevice {
    IN, OUT;

    public static EPeripheralDevice from(String type) {
        return EPeripheralDevice.valueOf(type);
    }
}

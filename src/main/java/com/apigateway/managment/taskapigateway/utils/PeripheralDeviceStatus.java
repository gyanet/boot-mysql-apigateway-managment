package com.apigateway.managment.taskapigateway.utils;

public enum PeripheralDeviceStatus {
    ONLINE("online"), OFFLINE("offline");

    public final String value;

    private PeripheralDeviceStatus(String value) {
        this.value = value;
    }

    private String getValue(){ return this.value; }
}

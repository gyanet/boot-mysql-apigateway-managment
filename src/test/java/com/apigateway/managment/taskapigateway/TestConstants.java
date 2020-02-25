package com.apigateway.managment.taskapigateway;

import com.apigateway.managment.taskapigateway.utils.PeripheralDeviceStatus;

public interface TestConstants {
    public static final String GATEWAY_NAME           = "pos-1-0.7tir.sepanta.net";
    public static final String GATEWAY_SERIAL_NUMBER  = "AKSJUALKI901HBGWUHA";
    public static final String GATEWAY_DUPLICATED_SERIAL_NUMBER  = "AKSJUALKI901HBGWUHB";
    public static final String GATEWAY_IP_ADDRESS     = "34.212.22.224";
    public static final String GATEWAY_DUPLICATED_IP_ADDRESS     = "34.212.22.225";
    public static final String GATEWAY_BAD_IP_ADDRESS = "34.212,22.224.0";
    public static final Long GATEWAY_ID               = Long.parseLong("1");
    public static final Long GATEWAY_BAD_ID           = Long.parseLong("10");
    public static final Long DEVICE_ID                = Long.parseLong("1");
    public static final Long DEVICE_ID_2              = Long.parseLong("2");
    public static final Long DEVICE_BAD_ID            = Long.parseLong("6");
    public static final Long DEVICE_UID               = Long.parseLong("9654");
    public static final String DEVICE_VENDOR          = "Soylent Corp";
    public static final String DEVICE_STATUS          = "ONLINE";
    public static final String DEVICE_STATUS_OFFLINE  = "OFFLINE";
    public static final String API_GATEWAY_URI        = "/api/gateway";

}

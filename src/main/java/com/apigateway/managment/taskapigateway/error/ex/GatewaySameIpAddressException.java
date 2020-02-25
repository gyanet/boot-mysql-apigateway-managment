package com.apigateway.managment.taskapigateway.error.ex;

public class GatewaySameIpAddressException extends Exception {
    public GatewaySameIpAddressException(String message) {
        super(message);
    }
}

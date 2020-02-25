package com.apigateway.managment.taskapigateway.utils;

public enum ResponseType {
    ERROR("ERROR"), INFO("INFO"), WARN("WARNING");

    public final String value;

    private ResponseType(String value) {
        this.value = value;
    }
}

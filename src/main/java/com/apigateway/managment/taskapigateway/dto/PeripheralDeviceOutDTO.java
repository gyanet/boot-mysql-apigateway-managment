package com.apigateway.managment.taskapigateway.dto;

import com.apigateway.managment.taskapigateway.model.EPeripheralDevice;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.util.Date;

@Value
@EqualsAndHashCode(callSuper = true)
public class PeripheralDeviceOutDTO extends PeripheralDeviceBaseDTO {
    private String ownOutField;

    @ConstructorProperties({"id","uid","vendor","dateCreated","status","ownOutField","type"})
    @Builder(toBuilder = true, builderMethodName = "builderFromPeripheralDeviceOutDTO")
    public PeripheralDeviceOutDTO(Long id, Long uid, String vendor, Date dateCreated, String status, String ownOutField, EPeripheralDevice type) {
        super(id, uid, vendor, dateCreated, status, type);
        this.ownOutField = ownOutField;
    }

}

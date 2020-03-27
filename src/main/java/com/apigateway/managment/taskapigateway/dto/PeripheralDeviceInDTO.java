package com.apigateway.managment.taskapigateway.dto;

import com.apigateway.managment.taskapigateway.model.EPeripheralDevice;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.util.Date;

@Value
@EqualsAndHashCode(callSuper = true)
public class PeripheralDeviceInDTO extends PeripheralDeviceBaseDTO {
    private String ownInField;

    @ConstructorProperties({"id","uid","vendor","dateCreated","status","ownInField","type"})
    @Builder(toBuilder = true, builderMethodName = "builderFromPeripheralDeviceInDTO")
    public PeripheralDeviceInDTO(Long id, Long uid, String vendor, Date dateCreated, String status, String ownInField, EPeripheralDevice type) {
        super(id, uid, vendor, dateCreated, status, type);
        this.ownInField = ownInField;
    }

}

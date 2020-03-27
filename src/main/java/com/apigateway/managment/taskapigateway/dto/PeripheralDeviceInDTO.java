package com.apigateway.managment.taskapigateway.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.beans.ConstructorProperties;
import java.util.Date;

@Data
@Value
//@EqualsAndHashCode(callSuper = true)
public class PeripheralDeviceInDTO extends PeripheralDeviceBaseDTO {
    private String ownInField;

    @ConstructorProperties({"id","uid","vendor","dateCreated","status","ownInField"})
    @Builder(toBuilder = true, builderMethodName = "builderFromPeripheralDeviceInDTO")
    public PeripheralDeviceInDTO(Long id, Long uid, String vendor, Date dateCreated, String status, String ownInField) {
        super(id, uid, vendor, dateCreated, status);
        this.ownInField = ownInField;
    }

}

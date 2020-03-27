package com.apigateway.managment.taskapigateway.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;

import java.beans.ConstructorProperties;
import java.util.Date;

@Data
@Value
//@EqualsAndHashCode(callSuper = true)
public class PeripheralDeviceOutDTO extends PeripheralDeviceBaseDTO {
    private String ownOutField;

    @ConstructorProperties({"id","uid","vendor","dateCreated","status","ownOutField"})
    @Builder(toBuilder = true, builderMethodName = "builderFromPeripheralDeviceOutDTO")
    public PeripheralDeviceOutDTO(Long id, Long uid, String vendor, Date dateCreated, String status, String ownOutField) {
        super(id, uid, vendor, dateCreated, status);
        this.ownOutField = ownOutField;
    }

}

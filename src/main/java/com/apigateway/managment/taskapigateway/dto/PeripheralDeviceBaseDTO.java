package com.apigateway.managment.taskapigateway.dto;

import com.apigateway.managment.taskapigateway.model.EPeripheralDevice;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = PeripheralDeviceInDTO.class, name = "IN"),
        @JsonSubTypes.Type(value = PeripheralDeviceOutDTO.class, name = "OUT")
})
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class PeripheralDeviceBaseDTO implements Serializable {

    private static final long serialVersionUID = 9043563622743672733L;

    protected Long id;
    protected Long uid;
    protected String vendor;
    protected Date dateCreated;
    protected String status;
    protected EPeripheralDevice type;

    /*public PeripheralDeviceBaseDTO(Long uid, String vendor, Date dateCreated, String status) {
        this.uid = uid;
        this.vendor = vendor;
        this.dateCreated = dateCreated;
        this.status = status;
//        this.type = type;
    }*/

}

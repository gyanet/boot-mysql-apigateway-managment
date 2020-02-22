package com.apigateway.managment.taskapigateway.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PeripheralDeviceGatewayDTO implements Serializable {
    private Long id;
    private Long uid;
    private String vendor;
    private  String status;
    private Date dateCreated;
    private String gatewaySerialNumber;
}

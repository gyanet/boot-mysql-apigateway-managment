package com.apigateway.managment.taskapigateway.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GatewayDTO implements Serializable {
    private Long id;
    private String serialNumber;
    private String name;
    private String ipAddress;
    private List<PeripheralDeviceDTO> peripheralDevices;

}

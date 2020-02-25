package com.apigateway.managment.taskapigateway.service;

import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.dto.ResponseDTO;
import com.apigateway.managment.taskapigateway.error.ex.*;
import com.apigateway.managment.taskapigateway.model.Gateway;

import java.util.List;

public interface IGatewayService {

    Gateway findById(Long idGateway) throws GatewayException, GatewayNotFoundException;
    Gateway add(GatewayDTO gatewayDTO) throws GatewayException, GatewayDataValidationException;
    List<GatewayDTO> getAll() throws GatewayException;
    ResponseDTO deletePeripheralDeviceFromGateway(Long deviceId, Long gatewayId) throws GatewayException, GatewayNotFoundException, PeripheralDeviceNotFoundException;
    ResponseDTO addPeripheralDeviceToGateway(PeripheralDeviceDTO deviceDTO, Long gatewayId) throws GatewayException, GatewayNotFoundException, PeripheralDeviceException;
}

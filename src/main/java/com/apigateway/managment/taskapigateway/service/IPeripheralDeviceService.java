package com.apigateway.managment.taskapigateway.service;

import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.error.ex.GatewayException;
import com.apigateway.managment.taskapigateway.error.ex.GatewayNotFoundException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceNotFoundException;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;

public interface IPeripheralDeviceService {
    PeripheralDevice findById(Long idPeripheral) throws PeripheralDeviceNotFoundException, PeripheralDeviceException;
    void addPeripheralDeviceToGateway(PeripheralDeviceDTO peripheralDevice, long idGateway) throws GatewayNotFoundException, PeripheralDeviceException, GatewayException;
    void deletePeripheralDeviceFromGateway(Long peripheralId, long idGateway) throws GatewayNotFoundException, PeripheralDeviceNotFoundException, PeripheralDeviceException, GatewayException;
}

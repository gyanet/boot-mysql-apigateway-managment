package com.apigateway.managment.taskapigateway.service;

import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceInDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceOutDTO;

public interface IPeripheralDeviceInheritanceTestService {
    PeripheralDeviceInDTO findInById(Long idPeripheral) throws Exception;
    PeripheralDeviceOutDTO findOutById(Long idPeripheral) throws Exception;
}

package com.apigateway.managment.taskapigateway.service.implementation;

import com.apigateway.managment.taskapigateway.annotations.BusinessService;
import com.apigateway.managment.taskapigateway.annotations.MethodInfo;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceInDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceOutDTO;
import com.apigateway.managment.taskapigateway.service.IPeripheralDeviceInheritanceTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@BusinessService
public class PeripheralDeviceInheritanceTestService implements IPeripheralDeviceInheritanceTestService {
    Logger logger = LoggerFactory.getLogger(PeripheralDeviceInheritanceTestService.class);

    @Override
    @MethodInfo(author = "Giselle Yanet",comments = "Test inheritance with Lombok PeripheralDeviceInDTO", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public PeripheralDeviceInDTO findInById(Long idPeripheral) throws Exception {
       logger.info(idPeripheral.toString());
        return PeripheralDeviceInDTO.builderFromPeripheralDeviceInDTO()
                .id(idPeripheral)
                .ownInField("ownFieldValue")
                .build();
    }

    @MethodInfo(author = "Giselle Yanet",comments = "Test PeripheralDeviceInDTO as parameter", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public PeripheralDeviceInDTO testDeviceAsInParameter(PeripheralDeviceInDTO peripheralDeviceInDTO) throws Exception {
        logger.info(peripheralDeviceInDTO.toString());
        return peripheralDeviceInDTO;
    }

    @Override
    @MethodInfo(author = "Giselle Yanet",comments = "Test inheritance with Lombok PeripheralDeviceOutDTO", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public PeripheralDeviceOutDTO findOutById(Long idPeripheral) throws Exception {
        logger.info(idPeripheral.toString());
        return PeripheralDeviceOutDTO.builderFromPeripheralDeviceOutDTO()
                .id(idPeripheral)
                .ownOutField("ownFieldValue")
                .build();
    }
}

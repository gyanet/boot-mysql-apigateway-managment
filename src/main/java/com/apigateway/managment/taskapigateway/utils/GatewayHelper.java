package com.apigateway.managment.taskapigateway.utils;

import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.error.ex.GatewayNotFoundException;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import com.apigateway.managment.taskapigateway.repository.PeripheralDeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GatewayHelper {
    @Autowired private ModelMapper modelMapper;
    @Autowired private GatewayRepository gatewayRepository;
    @Autowired private PeripheralDeviceRepository deviceRepository;

    public Gateway getyById(long idGateway) throws GatewayNotFoundException {
        return gatewayRepository.findById(idGateway)
                .orElseThrow(() -> new GatewayNotFoundException(String.format("There is no gateway with id %s", idGateway)));
    }


}

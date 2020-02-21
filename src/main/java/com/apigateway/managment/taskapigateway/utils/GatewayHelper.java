package com.apigateway.managment.taskapigateway.utils;

import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.error.ex.GatewayNotFoundException;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GatewayHelper {
    @Autowired private ModelMapper modelMapper;
    @Autowired private GatewayRepository gatewayRepository;

    public Gateway getyById(long idGateway) throws GatewayNotFoundException {
        return gatewayRepository.findById(idGateway)
                .orElseThrow(() -> new GatewayNotFoundException(String.format("There is no gateway with this id %s", idGateway)));
    }

    public GatewayDTO convertEntityToDTO(Gateway gateway) {
        return modelMapper.map(gateway, GatewayDTO.class);
    }

    public Gateway convertDTOtoEntity(GatewayDTO gatewayDTO) {return modelMapper.map(gatewayDTO, Gateway.class); }

    public List<GatewayDTO> convertEntityListToDTO(List<Gateway> gateways) {
        return gateways.stream()
                .map(gateway -> convertEntityToDTO(gateway))
                .collect(Collectors.toList());
    }

    public boolean existGatewayWithIpAddress(String ipAddress) {
        return gatewayRepository.findGatewayByIpAddress(ipAddress).isPresent();
    }

    public boolean existGatewayWithSerialNumber(String serialNumber) {
        return gatewayRepository.findGatewayBySerialNumber(serialNumber).isPresent();
    }

    public void setGatewayToPeripheralDevicesBeforeCreate(Gateway gateway) {
        gateway.getPeripheralDevices().forEach(peripheralDevice -> peripheralDevice.setGateway(gateway));
    }

}

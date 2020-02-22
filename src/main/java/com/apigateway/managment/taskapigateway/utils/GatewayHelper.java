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

    public Gateway findBySerialNumber(String gatewaySerialNumber) throws GatewayNotFoundException {
        return gatewayRepository.findGatewayBySerialNumber(gatewaySerialNumber)
                .orElseThrow(() -> new GatewayNotFoundException(String.format("There is no gateway with this serial number %s", gatewaySerialNumber)));
    }

    public GatewayDTO convertEntityToDTO(Gateway gateway) {
        return modelMapper.map(gateway, GatewayDTO.class);
    }

    public Gateway convertDTOtoEntity(GatewayDTO gatewayDTO) {
        return modelMapper.map(gatewayDTO, Gateway.class);
    }

    public List<GatewayDTO> convertEntityListToDTO(List<Gateway> gateways) {
        return gateways.stream()
                .map(gateway -> convertEntityToDTO(gateway))
                .collect(Collectors.toList());
    }

    public void savePeripheralDevicesAfterCreateGateway(Gateway gateway) {
        Set<PeripheralDevice> peripheralSetToStore = gateway.getPeripheralDevices();
        gateway.setPeripheralDevices(null);
        gatewayRepository.save(gateway);
        peripheralSetToStore.forEach(peripheralDevice -> {
            peripheralDevice.setGateway(gateway);
            deviceRepository.save(peripheralDevice);
        });
    }

    public void deletePeripheralDeviceFromGateway(Long deviceId) throws GatewayNotFoundException {
        deviceRepository.deleteById(deviceId);
    }

    public void deletePeripheralDevicesFromGateway(Set<PeripheralDevice> deviceSet) throws GatewayNotFoundException {
        deviceSet.forEach(peripheralDevice -> deviceRepository.delete(peripheralDevice));
    }

}

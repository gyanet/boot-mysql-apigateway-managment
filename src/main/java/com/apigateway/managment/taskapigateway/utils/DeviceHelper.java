package com.apigateway.managment.taskapigateway.utils;

import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceNotFoundException;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.PeripheralDeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DeviceHelper {
    @Autowired private ModelMapper modelMapper;
    @Autowired private PeripheralDeviceRepository deviceRepository;

    public PeripheralDevice getById(long idDevice) throws PeripheralDeviceNotFoundException {
        return deviceRepository.findById(idDevice)
                .orElseThrow(() -> new PeripheralDeviceNotFoundException(String.format("There is no peripheral device with this id %s", idDevice)));
    }

    public PeripheralDeviceDTO convertEntityToDTO(PeripheralDevice device) {
        return modelMapper.map(device, PeripheralDeviceDTO.class);
    }

    public PeripheralDevice convertDTOtoEntity(PeripheralDeviceDTO deviceDTO) {
        return modelMapper.map(deviceDTO, PeripheralDevice.class);
    }

    public PeripheralDevice findPeripheralDeviceBeforeDeleteFromGateway(Long peripheralId) throws PeripheralDeviceException {
        try {
            return getById(peripheralId);
        } catch (Exception e) {
            throw new PeripheralDeviceException(String.format("An error occurred searching a peripheral device: %s", e.getMessage()));
        }
    }

    public void addPeripheralDeviceToGatewayFound(PeripheralDevice peripheralDevice, Gateway gatewayFound) {
        Set<PeripheralDevice> deviceSet = new HashSet<>();
        peripheralDevice.setGateway(gatewayFound);
        deviceSet.add(peripheralDevice);

        gatewayFound.getPeripheralDevices().addAll(deviceSet);
    }

}

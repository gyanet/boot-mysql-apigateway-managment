package com.apigateway.managment.taskapigateway.utils;

import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceNotFoundException;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.PeripheralDeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceHelper {
    @Autowired private PeripheralDeviceRepository deviceRepository;
    @Autowired private ModelMapper modelMapper;

    public PeripheralDevice getById(long idDevice) throws PeripheralDeviceNotFoundException {

            return deviceRepository.findById(idDevice)
                    .orElseThrow(() -> new PeripheralDeviceNotFoundException(String.format("There is no peripheral device with id %s", idDevice)));
    }

    public PeripheralDevice convertDTOtoEntity(PeripheralDeviceDTO deviceDTO) throws PeripheralDeviceException {
        try {
            return modelMapper.map(deviceDTO, PeripheralDevice.class);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PeripheralDeviceException(String.format("An error occurred trying to convert peripheral device transfer object to entity with id %s : %s",deviceDTO.getId() ,ex.getMessage()));
        }
    }

}

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
    @Autowired private PeripheralDeviceRepository deviceRepository;
    @Autowired private GatewayHelper gatewayHelper;
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

   /* public void addPeripheralDeviceToGateway(PeripheralDevice peripheralDevice, Gateway gatewayFound) {
        Set<PeripheralDevice> deviceSet = new HashSet<>();
        peripheralDevice.setGateway(gatewayFound);
        deviceSet.add(peripheralDevice);

        gatewayFound.getPeripheralDevices().addAll(deviceSet);
    }*/

    /*public PeripheralDeviceDTO convertEntityToDTO(PeripheralDevice device) throws PeripheralDeviceException {
        try {
            return PeripheralDeviceDTO.builder()
                    .id(device.getId())
                    .uid(device.getUid())
                    .vendor(device.getVendor())
                    .dateCreated(device.getDateCreated())
                    .build();
        } catch (Exception e) {
            throw new PeripheralDeviceException(String.format("An error occurred trying to convert peripheral device to transfer object: %s", e.getMessage()));
        }

    } */

    /*public PeripheralDevice convertDTOtoEntity(PeripheralDeviceDTO deviceDTO) throws PeripheralDeviceException {
        try {
            Gateway gateway = gatewayHelper.findBySerialNumber(deviceDTO.getGatewaySerialNumber());

            return PeripheralDevice.builder()
                    .id(deviceDTO.getId())
                    .uid(deviceDTO.getUid())
                    .status(PeripheralDeviceStatus.valueOf(deviceDTO.getStatus()))
                    .dateCreated(deviceDTO.getDateCreated())
                    .build();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PeripheralDeviceException(String.format("An error occurred trying to convert peripheral device transfer object to entity with id %s : %s",deviceDTO.getId() ,ex.getMessage()));
        }
    }*/
    //        }
    //            throw new PeripheralDeviceException(String.format("An error occurred searching for peripheral device with id %s: %s",peripheralId , e.getMessage()));
    //        } catch (Exception e) {
    //            return getById(peripheralId);
    //        try {
//    public PeripheralDevice findPeripheralDeviceBeforeDeleteFromGateway(Long peripheralId) throws PeripheralDeviceException {

//    }

}

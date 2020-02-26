package com.apigateway.managment.taskapigateway.service.implementation;

import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.error.ex.GatewayException;
import com.apigateway.managment.taskapigateway.error.ex.GatewayNotFoundException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceNotFoundException;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import com.apigateway.managment.taskapigateway.repository.PeripheralDeviceRepository;
import com.apigateway.managment.taskapigateway.service.IPeripheralDeviceService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PeripheralDeviceService implements IPeripheralDeviceService {
    @Autowired private GatewayService gatewayService;
    @Autowired private ModelMapper modelMapper;
    @Autowired private PeripheralDeviceRepository deviceRepository;
    @Autowired private GatewayRepository gatewayRepository;

    Logger logger = LoggerFactory.getLogger(PeripheralDeviceService.class);

    public PeripheralDevice findById(Long idPeripheral) throws PeripheralDeviceNotFoundException, PeripheralDeviceException {
        logger.info("[PeripheralDeviceService] - findById {} " + idPeripheral);
        try {
            return deviceRepository.findById(idPeripheral)
                    .orElseThrow(() -> new PeripheralDeviceNotFoundException(String.format("There is no peripheral device with id %s", idPeripheral)));
        } catch (PeripheralDeviceNotFoundException e) {
            throw e;
        } catch (Exception ex) {
            throw new PeripheralDeviceException(String.format("An error occurred trying get peripheral device by id %s : %s", idPeripheral , ex.getMessage()));
        }
    }

    @Override
    public void addPeripheralDeviceToGateway(PeripheralDeviceDTO peripheralDevice, long idGateway) throws GatewayNotFoundException, PeripheralDeviceException, GatewayException {
        logger.info("[GatewayService] - savePeripheralDeviceToGateway {} " + "gatewayId: " + idGateway + " device: " + peripheralDevice.getUid());
        Gateway gateway = gatewayService.findById(idGateway);
        PeripheralDevice device = convertDTOtoEntity(peripheralDevice);
        device.setGateway(gateway);
        deviceRepository.save(device);
    }

    @Override
    public void deletePeripheralDeviceFromGateway(Long deviceId, long idGateway) throws GatewayNotFoundException, PeripheralDeviceNotFoundException, PeripheralDeviceException, GatewayException {
        logger.info("[GatewayService] - deletePeripheralDeviceFromGateway {} " + "device: " + deviceId);
        Gateway gateway = gatewayService.findById(idGateway);
        PeripheralDevice device = findById(deviceId);
        gateway.getPeripheralDevices().remove(device);
        gatewayRepository.save(gateway);
    }

    public PeripheralDevice convertDTOtoEntity(PeripheralDeviceDTO deviceDTO) throws PeripheralDeviceException {
        try {
            return modelMapper.map(deviceDTO, PeripheralDevice.class);
        } catch (Exception ex) {
            throw new PeripheralDeviceException(String.format("An error occurred trying to convert peripheral device transfer object to entity with id %s : %s",deviceDTO.getId() ,ex.getMessage()));
        }
    }

}

package com.apigateway.managment.taskapigateway.service.implementation;

import com.apigateway.managment.taskapigateway.annotations.BusinessService;
import com.apigateway.managment.taskapigateway.annotations.MethodInfo;
import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.dto.ResponseDTO;
import com.apigateway.managment.taskapigateway.error.ex.*;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import com.apigateway.managment.taskapigateway.repository.PeripheralDeviceRepository;
import com.apigateway.managment.taskapigateway.service.IGatewayService;
import com.apigateway.managment.taskapigateway.utils.GatewayValidator;
import com.apigateway.managment.taskapigateway.utils.ResponseType;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@BusinessService
public class GatewayService implements IGatewayService {
    @Autowired private PeripheralDeviceService deviceService;
    @Autowired private GatewayValidator validator;
    @Autowired private GatewayRepository gatewayRepository;
    @Autowired private PeripheralDeviceRepository deviceRepository;
    @Autowired private ModelMapper modelMapper;

    Logger logger = LoggerFactory.getLogger(GatewayService.class);

    @Override
    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public Gateway findById(Long idGateway) throws GatewayNotFoundException {
        logger.info("[GatewayService] - findById {} " + "idGateway: " + idGateway);
        try {
            return gatewayRepository.findById(idGateway)
                    .orElseThrow(() -> new GatewayNotFoundException(String.format("There is no gateway with id %s", idGateway)));
        } catch (Exception ex) {
            throw ex;
        }
    }

    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public GatewayDTO getById(Long idGateway) throws GatewayException, GatewayNotFoundException {
        logger.info("[GatewayService] - findById {} " + "idGateway: " + idGateway);
        try {
            return convertEntityToDTO(findById(idGateway));
        } catch (GatewayNotFoundException e) {
            throw e;
        } catch (Exception ex) {
            throw new GatewayException(String.format("An error occurred getting a gateway by id: %s", ex.getMessage()));
        }
    }

    @Override
    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public List<GatewayDTO> getAll() throws GatewayException {
        logger.info("[GatewayService] - getAll {}");
        try {
            return convertEntityListToDTO(gatewayRepository.findAll());
        } catch (Exception e) {
            throw new GatewayException(String.format("An error occurred getting the gateway list: %s", e.getMessage()));
        }
    }

    @Override
    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public Gateway add(GatewayDTO gatewayDTO) throws GatewayException, GatewayDataValidationException {
        logger.info("[GatewayService] - add {} " + "idGateway: " + gatewayDTO.getSerialNumber());
        try {
            Gateway gateway = convertDTOtoEntity(gatewayDTO);
            validator.validateGatewayData(gateway);
            return savePeripheralDevicesAfterCreateGateway(gateway);

        } catch (GatewayDataValidationException e) {
            throw e;
        } catch (Exception ex) {
            throw new GatewayException(String.format("An error occurred while persisting a gateway: %s", ex.getMessage()));
        }
    }

    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public Gateway savePeripheralDevicesAfterCreateGateway(Gateway gateway) throws GatewayException {
        logger.info("[GatewayService] - savePeripheralDevicesAfterCreateGateway {} " + "idGateway: " + gateway.getSerialNumber());
        try {
            Set<PeripheralDevice> peripheralSetToStore = gateway.getPeripheralDevices();
            gateway.setPeripheralDevices(null);
            gatewayRepository.save(gateway);
            peripheralSetToStore.forEach(peripheralDevice -> {
                peripheralDevice.setGateway(gateway);
                deviceRepository.save(peripheralDevice);
            });
            return gateway;
        } catch (NullPointerException e) {
            throw new GatewayException(String.format("A gateway most have al least one peripheral device."));
        } catch (Exception e) {
            throw new GatewayException(String.format("An error occurred while saving peripheral devices to gateway: %s", e.getMessage()));
        }
    }

    @Override
    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public ResponseDTO deletePeripheralDeviceFromGateway(Long deviceId, Long gatewayId) throws GatewayException, GatewayNotFoundException, PeripheralDeviceNotFoundException {
        logger.info("[GatewayService] - deletePeripheralDeviceFromGateway {} " + "deviceId: " + deviceId);
        try {
            deviceService.deletePeripheralDeviceFromGateway(deviceId, gatewayId);
            return new ResponseDTO(ResponseType.INFO, String.format("PeripheralDevice with id %s was successfully removed from gateway with id %S.", deviceId, gatewayId),  new Date());
        } catch (GatewayNotFoundException e) {
           throw e;
        } catch (PeripheralDeviceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new GatewayException(String.format("An error occurred while deleting a peripheral device with id %s from the gateway with id %s: %s",deviceId ,gatewayId ,e.getMessage()));
        }
    }

    @Override
    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public ResponseDTO addPeripheralDeviceToGateway(PeripheralDeviceDTO deviceDTO, Long gatewayId) throws GatewayException, GatewayNotFoundException, PeripheralDeviceException {
        logger.info("[GatewayService] - addPeripheralDeviceToGateway {} " + "gatewayId: " + gatewayId + "deviceDTO: " + deviceDTO.getUid());
        try {
            deviceService.addPeripheralDeviceToGateway(deviceDTO,gatewayId);
            return new ResponseDTO(ResponseType.INFO, String.format("Peripheral Device was successfully created with uid:%s.", deviceDTO.getUid()), new Date());
        } catch (GatewayNotFoundException e) {
            throw  e;
        } catch (PeripheralDeviceException e) {
            throw e;
        }catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new GatewayException(String.format("An error occurred adding a peripheral device to gateway with id %s: %s", gatewayId, e.getMessage()));
        }
    }

    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public GatewayDTO convertEntityToDTO(Gateway gateway) {
        return modelMapper.map(gateway, GatewayDTO.class);
    }

    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public Gateway convertDTOtoEntity(GatewayDTO gatewayDTO) {
        return modelMapper.map(gatewayDTO, Gateway.class);
    }

    @MethodInfo(author = "Giselle Yanet",comments = "", date = "25 March 2020", expireDate = "26 March 2020", revision = 1)
    public List<GatewayDTO> convertEntityListToDTO(List<Gateway> gateways) {
        logger.info("[GatewayService] - convertEntityListToDTO {} " + "gateways: " + gateways.size());
        return gateways.stream()
                .map(gateway -> convertEntityToDTO(gateway))
                .collect(Collectors.toList());
    }

}

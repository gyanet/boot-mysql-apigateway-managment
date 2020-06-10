package com.apigateway.managment.taskapigateway.service.implementation;

import com.apigateway.managment.taskapigateway.TestConstants;
import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.error.ex.*;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import com.apigateway.managment.taskapigateway.repository.PeripheralDeviceRepository;
import com.apigateway.managment.taskapigateway.utils.GatewayValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GatewayServiceTest implements TestConstants {
    @Rule
    public ExpectedException fails = ExpectedException.none();

    @Mock GatewayRepository repository;
    @Mock PeripheralDeviceRepository deviceRepository;
    @Mock GatewayValidator validator;
    @Mock PeripheralDeviceService deviceService;
    @Mock ModelMapper mapper;
    @InjectMocks private GatewayService service;

    private PeripheralDeviceDTO peripheralDeviceDTO = null;
    private PeripheralDevice peripheralDevice = null;
    private Gateway gateway = null;
    private GatewayDTO gatewayDTO = null;
    private List<PeripheralDeviceDTO> deviceDTOList = new ArrayList<>();
    private Set<PeripheralDevice> deviceSet = new HashSet<>();


    @Before
    public void setUp() throws Exception {
        deviceDTOList = new ArrayList<>();
        PeripheralDeviceDTO pd1 = PeripheralDeviceDTO.builder()
                .uid(Long.parseLong("9653"))
                .vendor(DEVICE_VENDOR)
                .status(DEVICE_STATUS)
                .dateCreated(new Date())
                .build();
        deviceDTOList.add(pd1);

        deviceDTOList.add(pd1);

        gateway = Gateway.newInstance()
                .id(GATEWAY_ID)
                .name(GATEWAY_NAME)
                .serialNumber(GATEWAY_SERIAL_NUMBER)
                .ipAddress(GATEWAY_IP_ADDRESS)
                .peripheralDevices(deviceSet)
                .build();

        gatewayDTO = GatewayDTO.builder()
                .id(GATEWAY_ID)
                .name(GATEWAY_NAME)
                .serialNumber(GATEWAY_SERIAL_NUMBER)
                .ipAddress(GATEWAY_IP_ADDRESS)
                .peripheralDevices(deviceDTOList)
                .build();

        peripheralDevice = PeripheralDevice.newInstance()
                .id(DEVICE_ID)
                .uid(pd1.getUid())
                .vendor(DEVICE_VENDOR)
                .status(DEVICE_STATUS)
                .gateway(gateway)
                .dateCreated(pd1.getDateCreated())
                .build();

        deviceSet.add(peripheralDevice);

        when(repository.findById(GATEWAY_ID)).thenReturn(Optional.of(gateway));
        when(repository.findById(GATEWAY_BAD_ID)).thenReturn(Optional.empty());

        when(repository.save(any(Gateway.class))).thenReturn(gateway);
        when(deviceRepository.save(any(PeripheralDevice.class))).thenReturn(peripheralDevice);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void whenFindByIdRepositoryGetGatewayWithCorrectIdCorrectTimes() throws GatewayNotFoundException, GatewayException {

        ArgumentCaptor<Long> arg = ArgumentCaptor.forClass(Long.class);

        Gateway gateway = service.findById(GATEWAY_ID);

        verify(repository, times(1)).findById(GATEWAY_ID);

        verify(repository).findById(arg.capture());
        assertEquals(GATEWAY_ID, arg.getValue());

    }

    @Test(expected = GatewayNotFoundException.class)
    public void whenFindByIdRepositoryGetDeviceWithNoneExistingId() throws GatewayNotFoundException, GatewayException {

        service.findById(GATEWAY_BAD_ID);
        verify(repository, times(1)).findById(GATEWAY_BAD_ID);
    }

    @Test
    public void whenGetAllGatewaysOk() throws GatewayException {
        when(repository.findAll()).thenReturn(Arrays.asList(gateway));
        when(mapper.map(gateway, GatewayDTO.class)).thenReturn(gatewayDTO);

        List<GatewayDTO> list = service.getAll();

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(gateway, GatewayDTO.class);
        assertTrue(!list.isEmpty());
        assertTrue(list.size() == 1);
        assertTrue(list.contains(gatewayDTO));
    }

    @Test(expected = GatewayException.class)
    public void whenGetAllGatewaysNull() throws GatewayException {
        when(repository.findAll()).thenReturn(null);
        service.getAll();

        verify(repository, times(1)).findAll();
        verify(mapper, times(0)).map(null, GatewayDTO.class);
    }

    @Test
    public void whenSavePeripheralDevicesAfterCreateGatewayDataOk() throws GatewayException, GatewayDataValidationException {
        service.savePeripheralDevicesAfterCreateGateway(gateway);

        verify(repository, times(1)).save(any());
        verify(deviceRepository, times(1)).save(any());
    }

    @Test(expected = GatewayException.class)
    public void whenSavePeripheralDevicesAfterCreateGatewayDevicesNullProduceGatewayException() throws GatewayException {
        gateway.setPeripheralDevices(null);
        service.savePeripheralDevicesAfterCreateGateway(gateway);

        verify(repository, times(1)).save(any());
        verify(deviceRepository, times(0)).save(any());
    }

    @Test
    public void whenAddGatewayAndPripheralsWithDataOk() throws GatewayDataValidationException, GatewayException {
        when(mapper.map(gatewayDTO, Gateway.class)).thenReturn(gateway);

        Gateway gate = service.add(gatewayDTO);

        verify(mapper, times(1)).map(gatewayDTO, Gateway.class);
        verify(validator, times(1)).validateGatewayData(gateway);
        verify(repository, times(1)).save(gateway);
        verify(deviceRepository, times(1)).save(peripheralDevice);

        assertEquals(gatewayDTO.getId(), gateway.getId());

    }

    @Test(expected = GatewayException.class)
    public void whenAddGatewayAndPripheralsNullThrowsGatewayException() throws GatewayDataValidationException, GatewayException {
        gateway.setPeripheralDevices(null);
        when(mapper.map(gatewayDTO, Gateway.class)).thenReturn(gateway);

        Gateway gate = service.add(gatewayDTO);

        verify(mapper, atLeastOnce()).map(gatewayDTO, Gateway.class);
        verify(validator, times(1)).validateGatewayData(gateway);
        verify(repository, times(1)).save(gateway);
        verify(deviceRepository, times(0)).save(peripheralDevice);

    }

    @Test
    public void whenDeletePeripheralDeviceFromGatewayOK() throws GatewayException, PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException {
        service.deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_ID);
        verify(deviceService, atLeastOnce()).deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_ID);
    }

    @Test(expected = PeripheralDeviceNotFoundException.class)
    public void whenDeletePeripheralDeviceFromGatewayBadDeviceIp() throws GatewayException, PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException {
        when(service.deletePeripheralDeviceFromGateway(DEVICE_BAD_ID, GATEWAY_ID)).thenThrow(new PeripheralDeviceNotFoundException("Exception"));

        service.deletePeripheralDeviceFromGateway(DEVICE_BAD_ID, GATEWAY_ID);
        verify(deviceService, atLeastOnce()).deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_ID);
    }

    @Test(expected = GatewayNotFoundException.class)
    public void whenDeletePeripheralDeviceFromGatewayBadGatewayIp() throws GatewayException, PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException {
        when(service.deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_BAD_ID)).thenThrow(new GatewayNotFoundException("Exception"));

        service.deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_BAD_ID);
        verify(deviceService, atLeastOnce()).deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_ID);
    }

}
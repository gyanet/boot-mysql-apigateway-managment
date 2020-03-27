package com.apigateway.managment.taskapigateway.service.implementation;

import com.apigateway.managment.taskapigateway.TestConstants;
import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceInDTO;
import com.apigateway.managment.taskapigateway.error.ex.GatewayException;
import com.apigateway.managment.taskapigateway.error.ex.GatewayNotFoundException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceNotFoundException;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import com.apigateway.managment.taskapigateway.repository.PeripheralDeviceRepository;
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
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PeripheralDeviceServiceTest implements TestConstants {

    @Rule public ExpectedException fails = ExpectedException.none();

    @Mock PeripheralDeviceRepository repository;
    @Mock GatewayRepository gatewayRepository;
    @Mock GatewayService gatewayService;
    @Mock ModelMapper mapper;
    @InjectMocks private PeripheralDeviceService service;


    private PeripheralDeviceDTO peripheralDeviceDTO =null;
    private PeripheralDevice peripheralDevice = null;
    private PeripheralDevice peripheralDevice2 = null;
    private Gateway gateway = null;
    private GatewayDTO gatewayDTO = null;
    private Set<PeripheralDevice> deviceSet = new HashSet<>();
    private Set<PeripheralDevice> emptyDeviceSet = new HashSet<>();
    private List<PeripheralDeviceDTO> deviceDTOList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        gateway = Gateway.newInstance()
                .id(GATEWAY_ID)
                .serialNumber(GATEWAY_SERIAL_NUMBER)
                .name(GATEWAY_NAME)
                .ipAddress(GATEWAY_IP_ADDRESS)
                .peripheralDevices(deviceSet)
                .build();

        gatewayDTO = GatewayDTO.builder()
                .id(GATEWAY_ID)
                .serialNumber(GATEWAY_SERIAL_NUMBER)
                .name(GATEWAY_NAME)
                .ipAddress(GATEWAY_IP_ADDRESS)
                .peripheralDevices(deviceDTOList)
                .build();

        peripheralDevice = PeripheralDevice.newInstance()
                .id(DEVICE_ID)
                .uid(DEVICE_UID)
                .vendor(DEVICE_VENDOR)
                .status(DEVICE_STATUS)
                .gateway(gateway)
                .build();

        peripheralDeviceDTO = PeripheralDeviceDTO.builder()
                .id(DEVICE_ID)
                .uid(DEVICE_UID)
                .vendor(DEVICE_VENDOR)
                .status(DEVICE_STATUS)
                .dateCreated(new Date())
                .build();

        peripheralDevice2 = PeripheralDevice.newInstance()
                .id(DEVICE_ID + 1)
                .uid(Long.parseLong("9655"))
                .vendor(DEVICE_VENDOR)
                .status(DEVICE_STATUS)
                .gateway(gateway)
                .build();

        deviceSet.add(peripheralDevice);
        deviceSet.add(peripheralDevice2);

        when(repository.findById(DEVICE_ID)).thenReturn(Optional.of(peripheralDevice));
        when(repository.save(peripheralDevice)).thenReturn(peripheralDevice);
        when(gatewayRepository.save(gateway)).thenReturn(gateway);
        when(mapper.map(peripheralDeviceDTO, PeripheralDevice.class)).thenReturn(peripheralDevice);
        when(repository.findById(DEVICE_BAD_ID)).thenReturn(Optional.empty());

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void whenFindByIdRepositoryGetDeviceWithCorrectIdCorrectTimes() throws PeripheralDeviceNotFoundException, PeripheralDeviceException {

        ArgumentCaptor<Long> arg = ArgumentCaptor.forClass(Long.class);

        PeripheralDevice device = service.findById(DEVICE_ID);

        verify(repository, times(1))
                .findById(DEVICE_ID);

        verify(repository).findById(arg.capture());
        assertEquals(DEVICE_ID, arg.getValue());

        assertTrue(device != null);
        assertEquals(device.getId(), DEVICE_ID);

    }

    @Test
    public void whenFindByIdRepositoryGetDeviceWithNoneExistingId() throws PeripheralDeviceNotFoundException, PeripheralDeviceException {
        try {
            service.findById(DEVICE_BAD_ID);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("PeripheralDeviceNotFoundException"));
            assertTrue(e.getMessage().equals(String.format("There is no peripheral device with id %s", DEVICE_BAD_ID)));
        }
    }

    @Test
    public void whenAddPeripheralDeviceToGatewayWithCorrectIdCorrectTimes() throws PeripheralDeviceException, GatewayNotFoundException, GatewayException {

        when(gatewayService.findById(GATEWAY_ID)).thenReturn(gateway);
        ArgumentCaptor<Long> arg = ArgumentCaptor.forClass(Long.class);

        service.addPeripheralDeviceToGateway(peripheralDeviceDTO,GATEWAY_ID);

        verify(mapper, times(1)).map(peripheralDeviceDTO, PeripheralDevice.class);
        verify(gatewayService, times(1)).findById(GATEWAY_ID);
        verify(repository, times(1)).save(any(PeripheralDevice.class));

        verify(gatewayService).findById(arg.capture());
        assertEquals(GATEWAY_ID, arg.getValue());

    }

    @Test(expected = GatewayNotFoundException.class)
    public void whenAddPeripheralDeviceToGatewayWithNoneExistingId() throws PeripheralDeviceException, GatewayNotFoundException, GatewayException {
        when(gatewayService.findById(GATEWAY_BAD_ID)).thenThrow(new GatewayNotFoundException("Gateway Not Found."));
        ArgumentCaptor<Long> arg = ArgumentCaptor.forClass(Long.class);

            service.addPeripheralDeviceToGateway(peripheralDeviceDTO,GATEWAY_BAD_ID);

        verify(gatewayService, times(1)).findById(GATEWAY_BAD_ID);
        verify(mapper, times(0)).map(peripheralDeviceDTO, PeripheralDevice.class);
        verify(repository, times(0)).save(any(PeripheralDevice.class));

        verify(gatewayService).findById(arg.capture());
        assertEquals(GATEWAY_BAD_ID, arg.getValue());
    }

    @Test
    public void whenDeletePeripheralDeviceFromGatewayWithCorrectDataCorrectTimes() throws PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException, GatewayException {
        when(gatewayService.findById(GATEWAY_ID)).thenReturn(gateway);

        service.deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_ID);

        verify(gatewayService, times(1)).findById(GATEWAY_ID);
        verify(repository, times(1)).findById(DEVICE_ID);
        verify(gatewayRepository, times(1)).save(gateway);
    }

    @Test
    public void whenDeletePeripheralDeviceFromGatewayWithDevicesEmpty() throws PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException, GatewayException {
        gateway.setPeripheralDevices(emptyDeviceSet);
        when(gatewayService.findById(GATEWAY_ID)).thenReturn(gateway);

        service.deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_ID);

        verify(gatewayService, times(1)).findById(GATEWAY_ID);
        verify(repository, times(1)).findById(DEVICE_ID);
        verify(gatewayRepository, times(1)).save(gateway);
    }

    @Test(expected = NullPointerException.class)
    public void whenDeletePeripheralDeviceFromGatewayWithDevicesNullReturnNullPointerException() throws PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException, GatewayException {
        gateway.setPeripheralDevices(null);
        when(gatewayService.findById(GATEWAY_ID)).thenReturn(gateway);

        service.deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_ID);

        verify(gatewayService, times(1)).findById(GATEWAY_ID);
        verify(repository, times(1)).findById(DEVICE_ID);
        verify(gatewayRepository, times(0)).save(gateway);
    }

    @Test(expected = GatewayNotFoundException.class)
    public void whenDeletePeripheralDeviceFromGatewayWithGatewayBadGatewayId() throws PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException, GatewayException {
        when(gatewayService.findById(GATEWAY_BAD_ID)).thenThrow(new GatewayNotFoundException("Gateway Not Found."));

        service.deletePeripheralDeviceFromGateway(DEVICE_ID, GATEWAY_BAD_ID);

        verify(gatewayService, times(1)).getById(GATEWAY_BAD_ID);
        verify(repository, times(0)).findById(DEVICE_ID);
        verify(repository, times(0)).save(any(PeripheralDevice.class));
    }

    @Test(expected = PeripheralDeviceNotFoundException.class)
    public void whenDeletePeripheralDeviceFromGatewayWithBadDeviceId() throws PeripheralDeviceNotFoundException, GatewayNotFoundException, PeripheralDeviceException, GatewayException {

        service.deletePeripheralDeviceFromGateway(DEVICE_BAD_ID, GATEWAY_ID);

        verify(gatewayService, times(1)).getById(GATEWAY_ID);
        verify(repository, times(1)).findById(DEVICE_BAD_ID);
        verify(repository, times(0)).save(any(PeripheralDevice.class));
    }

    @Test
    public void whenConvertDTOToEntityOk() throws PeripheralDeviceException {
        PeripheralDevice device = service.convertDTOtoEntity(peripheralDeviceDTO);
        verify(mapper, times(1)).map(peripheralDeviceDTO, PeripheralDevice.class);
    }

}
package com.apigateway.managment.taskapigateway.utils;

import com.apigateway.managment.taskapigateway.TestConstants;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.repository.GatewayRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GatewayValidatorTest implements TestConstants {
    @Rule
    public ExpectedException fails = ExpectedException.none();

    @Mock GatewayRepository repository;
    @InjectMocks private GatewayValidator validator;

    private Set<PeripheralDevice> deviceSet = new HashSet<>();
    private Gateway gateway = null;

    @Before
    public void setUp() throws Exception {

        gateway = Gateway.builder()
                .id(GATEWAY_ID)
                .serialNumber(GATEWAY_SERIAL_NUMBER)
                .name(GATEWAY_NAME)
                .ipAddress(GATEWAY_IP_ADDRESS)
                .peripheralDevices(deviceSet)
                .build();

    }

    @After
    public void tearDown() throws Exception {
        gateway = null;
    }

    @Test
    public void whenValidateGatewayDevicesListIsNull(){
        try {
            validator.validateGatewayDevicesNumber(null);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("GatewayDataValidationException"));
            assertTrue(e.getMessage().equals("The gateway most have at least one peripheral device."));
        }
    }

    @Test
    public void whenValidateGatewayDevicesListIsEmpty(){
        try {
            validator.validateGatewayDevicesNumber(deviceSet);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("GatewayDataValidationException"));
            assertTrue(e.getMessage().equals("The gateway most have at least one peripheral device."));
        }
    }

    @Test
    public void whenValidateGatewayDevicesAreMoreThan10(){
        try {
            int i = 0;
            do {
                deviceSet.add(PeripheralDevice.builder().vendor(DEVICE_VENDOR + i).build());
                i++;
            } while (i<11);
            validator.validateGatewayDevicesNumber(deviceSet);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("GatewayDataValidationException"));
            assertTrue(e.getMessage().equals("No more than 10 devices are allowed per gateway."));
        }
    }

    @Test
    public void validateExistGatewayWithBadIpAddress(){
        try {
            validator.validateGatewayIpAddress(GATEWAY_BAD_IP_ADDRESS);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("GatewayDataValidationException"));
            assertTrue(e.getMessage().equals(String.format("The IP Address for this gateway is invalid: %s.", GATEWAY_BAD_IP_ADDRESS)));
        }
    }

    @Test
    public void validateExistGatewayWithExistingIpAddress() {
        try {
            validator.validateExistGatewayWithIpAddress(GATEWAY_BAD_IP_ADDRESS);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("GatewayDataValidationException"));
            assertTrue(e.getMessage().equals(String.format("The IP Address of the gateway already exist: %s.", GATEWAY_BAD_IP_ADDRESS)));
        }
    }

    @Test
    public void validateExistGatewayWithExistingSerialNumber() {
        try {
            validator.validateExistGatewayWithSerialNumber(GATEWAY_DUPLICATED_SERIAL_NUMBER);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("GatewayDataValidationException"));
            assertTrue(e.getMessage().equals(String.format("The serial number of this gateway already exist: %s.", GATEWAY_DUPLICATED_SERIAL_NUMBER)));
        }

    }

    @Test
    public void validateGatewayDataEmptyDevicesList() {
        try {
            validator.validateGatewayData(gateway);
        } catch (Exception e){
            assertTrue(e.getClass().getSimpleName().equals("GatewayDataValidationException"));
            assertTrue(e.getMessage().equals("The gateway most have at least one peripheral device."));
        }
    }
}
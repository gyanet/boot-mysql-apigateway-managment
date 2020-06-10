package com.apigateway.managment.taskapigateway.controller;

import com.apigateway.managment.taskapigateway.TestConstants;
import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.dto.ResponseDTO;
import com.apigateway.managment.taskapigateway.error.ApiErrorResponse;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.model.PeripheralDevice;
import com.apigateway.managment.taskapigateway.utils.ResponseType;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiGatewayControllerTest extends AbstractTest implements TestConstants {

    GatewayDTO createGateway = null;
    PeripheralDeviceDTO deviceForCreateGateway = null;
    PeripheralDeviceDTO deviceDTO = null;
    List<PeripheralDeviceDTO> deviceDTOList = new ArrayList<>();
    List<PeripheralDevice> deviceList = new ArrayList<>();

    @Before
    public void setUp() {
        deviceDTO = PeripheralDeviceDTO.builder()
                .uid(Long.parseLong("9653"))
                .vendor(DEVICE_VENDOR)
                .status(DEVICE_STATUS)
                .build();

        deviceForCreateGateway = PeripheralDeviceDTO.builder()
                .uid(Long.parseLong("9655"))
                .vendor(DEVICE_VENDOR)
                .status(DEVICE_STATUS)
                .build();

        createGateway = GatewayDTO.builder()
                .name("pos-1-0.7tir.sepanta2.net")
                .serialNumber("AKSJUALKI901HBWUHA")
                .ipAddress("34.212.23.224")
                .peripheralDevices(deviceDTOList)
                .build();

    }

    @After
    public void tearDown() {

    }

    @Test
    public void getGatewayByIdOk() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(API_GATEWAY_URI + '/'+ GATEWAY_ID)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        GatewayDTO result = super.mapFromJson(content, GatewayDTO.class);
        assertEquals(result.getIpAddress(), "34.212.22.224");
        assertEquals(result.getId(), GATEWAY_ID);
    }

    @Test
    public void getGatewayByIdNoExist() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(API_GATEWAY_URI + '/'+ GATEWAY_BAD_ID)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiErrorResponse result = super.mapFromJson(content, ApiErrorResponse.class);
        assertEquals(result.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assertEquals(result.getMessage(), THERE_IS_NO_GATEWAY_WITH_ID + GATEWAY_BAD_ID);
    }

    @Test
    public void getAllGateways() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .get(API_GATEWAY_URI)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        GatewayDTO[] resultList = super.mapFromJson(content, GatewayDTO[].class);
        assertTrue(resultList.length > 0);

    }

    @Test
    public void addGatewayDevicesOK() throws Exception {
        createGateway.getPeripheralDevices().add(deviceDTO);
        String inputJson = super.mapToJson(createGateway).replaceAll("%","\"");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post(API_GATEWAY_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseDTO result = super.mapFromJson(content, ResponseDTO.class);
        assertEquals(result.getType(), ResponseType.INFO);
    }

    @Test
    public void addGatewayDevicesEmptyThrowsConstraintViolationException() throws Exception {

        String inputJson = super.mapToJson(createGateway).replaceAll("%","\"");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post(API_GATEWAY_URI)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiErrorResponse result = super.mapFromJson(content, ApiErrorResponse.class);
        assertEquals(result.getMessage(), "The gateway most have at least one peripheral device.");
    }

    @Test
    public void deletePeripheralDeviceFromGatewayOk() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .delete(API_GATEWAY_URI + "/" + GATEWAY_ID + "/peripheral/" + DEVICE_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ResponseDTO result = super.mapFromJson(content, ResponseDTO.class);
        assertEquals(result.getMessage(),
                String.format("PeripheralDevice with id %s was successfully removed from gateway with id %S.", DEVICE_ID, GATEWAY_ID));
    }

    @Test
    public void deletePeripheralDeviceFromGatewayDeviceIdNotExist() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .delete(API_GATEWAY_URI + "/" + GATEWAY_ID + "/peripheral/" + DEVICE_BAD_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiErrorResponse result = super.mapFromJson(content, ApiErrorResponse.class);
        assertEquals(result.getMessage(),
                String.format("There is no peripheral device with id %S", DEVICE_BAD_ID));
    }

    @Test
    public void deletePeripheralDeviceFromGatewayGatewayIdNotExist() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .delete(API_GATEWAY_URI + "/" + GATEWAY_BAD_ID + "/peripheral/" + DEVICE_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiErrorResponse result = super.mapFromJson(content, ApiErrorResponse.class);
        assertEquals(result.getMessage(),
                String.format("There is no gateway with id %S", GATEWAY_BAD_ID));
    }

    @Test
    public void addPeripheralDeviceToGateway() throws Exception {
        String inputJson = super.mapToJson(deviceDTO).replaceAll("%","\"");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .put(API_GATEWAY_URI + "/" + GATEWAY_ID + "/peripheral")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }
}
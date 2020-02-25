package com.apigateway.managment.taskapigateway.controller;

import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.ResponseDTO;
import com.apigateway.managment.taskapigateway.error.ex.*;
import com.apigateway.managment.taskapigateway.model.Gateway;
import com.apigateway.managment.taskapigateway.service.implementation.GatewayService;
import com.apigateway.managment.taskapigateway.utils.ResponseType;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@Transactional
@RestController
@Description(value = "")
@RequestMapping(path = "/api/gateway")
@Api(value = "Gateway Management", description = "Store information about these gateways and their associated devices, as well as obtain information about the devices associated with the gateway.")
public class ApiGatewayController {

    @Autowired private GatewayService gatewayService;
    Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);

    @ApiOperation(value = "Get details of a gateway.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gateway found"),
            @ApiResponse(code = 404, message = "Gateway not found"),
            @ApiResponse(code = 500, message = "Error getting gateways detail")
    })
    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<GatewayDTO> getGatewayById(@ApiParam(value = "Id of gateway store", required = true) @PathVariable Long id) throws GatewayException, GatewayNotFoundException {
        logger.info("[ApiGatewayController] - getGatewayById {}");
        return new ResponseEntity<>(gatewayService.getById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Get all gateways stored.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all stored gateways."),
            @ApiResponse(code = 500, message = "Error getting all stored gateways.")
    })
    @GetMapping()
    @ResponseBody
    public ResponseEntity<List<GatewayDTO>> getAllGateways() throws GatewayException {
        logger.info("[ApiGatewayController] - getAllGateways {}");
            return new ResponseEntity<>(gatewayService.getAll(),HttpStatus.OK);
    }

    @ApiOperation(value = "Add a gateway and their peripherals.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Gateway created successfully."),
            @ApiResponse(code = 500, message = "An error occur creating gateway."),
    })
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseDTO> addGateway(@ApiParam(value = "Json with gateway data.", required = true) @RequestBody GatewayDTO gatewayDto) throws GatewayException, GatewayDataValidationException {
        logger.info("[ApiGatewayController] - addGateway {}");

            Gateway gateway = gatewayService.add(gatewayDto);
            final URI location = ServletUriComponentsBuilder
                    .fromCurrentServletMapping().path("/api/gateway/{id}").build()
                    .expand(gateway.getId()).toUri();

            final HttpHeaders headers = new HttpHeaders();
            headers.setLocation(location);

            return new ResponseEntity<>(new ResponseDTO(ResponseType.INFO, String.format("Gateway was successfully created with id %s and serial number:%s.", gateway.getId(), gateway.getSerialNumber()),
                    new Date()),headers, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete peripheral device from gateway.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Peripheral device deleted successfully from the gateway."),
            @ApiResponse(code = 500, message = "An error occur deleting peripheral device from the gateway."),
    })
    @DeleteMapping(path = "/{idGateway}/peripheral/{idDevice}")
    @ResponseBody
    public ResponseEntity<ResponseDTO> deletePeripheralDeviceFromGateway(@ApiParam(value = "Peripheral device id.", required = true) @PathVariable("idDevice") Long deviceId,
                                                                         @ApiParam(value = "Gateway id.", required = true) @PathVariable("idGateway") Long gatewayId) throws GatewayException, PeripheralDeviceNotFoundException, GatewayNotFoundException {
        logger.info("[ApiGatewayController] - deletePeripheralDeviceFromGateway {}");
            return new ResponseEntity<>(gatewayService.deletePeripheralDeviceFromGateway(deviceId, gatewayId), HttpStatus.OK);

    }

    @ApiOperation(value = "Add peripheral device to gateway.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Peripheral device added successfully."),
            @ApiResponse(code = 500, message = "An error occur adding peripheral device."),
    })
    @PutMapping(path = "/{idGateway}/peripheral", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseDTO> addPeripheralDeviceToGateway(@ApiParam(value = "Gateway id.", required = true) @PathVariable("idGateway") Long gatewayId,
                                                                    @ApiParam(value = "Json with peripheral device data.", required = true) @RequestBody PeripheralDeviceDTO deviceDTO) throws GatewayException, PeripheralDeviceException, GatewayNotFoundException {
        logger.info("[ApiGatewayController] - addPeripheralDeviceToGateway {}");
            return new ResponseEntity<>(gatewayService.addPeripheralDeviceToGateway(deviceDTO, gatewayId), HttpStatus.CREATED);
    }

}

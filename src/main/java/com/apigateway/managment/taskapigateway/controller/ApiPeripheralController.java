package com.apigateway.managment.taskapigateway.controller;

import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceException;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceNotFoundException;
import com.apigateway.managment.taskapigateway.service.implementation.PeripheralDeviceService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Description(value = "")
@RequestMapping(path = "/api/gateway/peripheral")
@Api(value = "Devices Gateway Management", description = "Update or remove devices from a gateway")
public class ApiPeripheralController {

    @Autowired private PeripheralDeviceService peripheralDeviceService;
    Logger logger = LoggerFactory.getLogger(ApiPeripheralController.class);

    @ApiOperation(value = "Add a device to gateway")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gateway updated"),
            @ApiResponse(code = 404, message = "Gateway not found"),
            @ApiResponse(code = 500, message = "Error updating gateways detail")
    })
    @PostMapping(path = "/{id}")
    public ResponseEntity<GatewayDTO> addDeviceToGateway(
            @ApiParam(value = "Json with device to be added to gateway", required = true) @RequestBody PeripheralDeviceDTO peripheralDeviceDto,
            @ApiParam(value = "Gateway id that will be updated", required = true) @PathVariable Long idGateway) throws PeripheralDeviceException {

            return new ResponseEntity<>(peripheralDeviceService.addPeripheralDeviceToGateway(peripheralDeviceDto, idGateway), HttpStatus.OK);
    }

   @ApiOperation(value = "Delete a device from a gateway.")
   @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Device updated"),
            @ApiResponse(code = 404, message = "Device not found"),
            @ApiResponse(code = 500, message = "Error deleting a device from gateway")
    })
    @DeleteMapping(path = "/{idGateway}/{idPeripheral}")
    public ResponseEntity<PeripheralDeviceDTO> deleteDeviceFromGateway(
            @ApiParam(value = "Json with device to be deleted", required = true) @RequestBody PeripheralDeviceDTO peripheralDeviceDto,
            @ApiParam(value = "Gateway id that will be updated", required = true) @PathVariable Long idGateway,
            @ApiParam(value = "Device id that will be delete", required = true) @PathVariable Long idPeripheral) throws PeripheralDeviceNotFoundException {

            return new ResponseEntity<PeripheralDeviceDTO>(peripheralDeviceService.deletePeripheralDeviceFromGateway(idGateway, idPeripheral), HttpStatus.OK);

    }

}

package com.apigateway.managment.taskapigateway.controller;

import com.apigateway.managment.taskapigateway.dto.GatewayDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceDTO;
import com.apigateway.managment.taskapigateway.error.ex.PeripheralDeviceException;
import com.apigateway.managment.taskapigateway.service.implementation.PeripheralDeviceService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Description(value = "")
@RequestMapping(path = "/api/gateway")
@Api(value = "Devices Gateway Management", description = "Update or remove devices from a gateway")
public class ApiPeripheralController {

    @Autowired
    private PeripheralDeviceService peripheralDeviceService;

    @ApiOperation(value = "Add a device to gateway")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gateway updated"),
            @ApiResponse(code = 404, message = "Gateway not found"),
            @ApiResponse(code = 500, message = "Error updating gateways detail")
    })
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<GatewayDTO> addDeviceToGateway(
            @ApiParam(value = "Json with device to be added to gateway", required = true) @RequestBody PeripheralDeviceDTO peripheralDeviceDto,
            @ApiParam(value = "Gateway id that will be updated", required = true) @PathVariable String id) throws PeripheralDeviceException {
        try {
            long idGateway = Long.parseLong(id);
            GatewayDTO gatewayDto = peripheralDeviceService.addOneToGateway(peripheralDeviceDto, idGateway);
            return new ResponseEntity<>(gatewayDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "Delete a device from a gateway.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Device updated"),
            @ApiResponse(code = 404, message = "Device not found"),
            @ApiResponse(code = 500, message = "Error deleting a device from gateway")
    })
    @DeleteMapping(path = "/delete/{idGateway}/{idPeripheral}")
    public ResponseEntity<PeripheralDeviceDTO> deleteGatewayDevice(
            @ApiParam(value = "Json with device to be deleted", required = true) @RequestBody PeripheralDeviceDTO peripheralDeviceDto,
            @ApiParam(value = "Gateway id that will be updated", required = true) @PathVariable String idGateway,
            @ApiParam(value = "Device id that will be delete", required = true) @PathVariable String idPeripheral) {
        try {
            long gatewayId = Long.parseLong(idGateway);
            long peripheralId = Long.parseLong(idPeripheral);
            return new ResponseEntity<PeripheralDeviceDTO>(peripheralDeviceService.deleteOneFromGateway(peripheralDeviceDto, gatewayId, peripheralId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

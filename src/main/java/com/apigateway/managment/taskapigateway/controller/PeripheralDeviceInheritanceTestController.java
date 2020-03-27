package com.apigateway.managment.taskapigateway.controller;

import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceInDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceOutDTO;
import com.apigateway.managment.taskapigateway.service.IPeripheralDeviceInheritanceTestService;
import com.apigateway.managment.taskapigateway.service.implementation.PeripheralDeviceInheritanceTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@Description(value = "")
@RequestMapping(path = "/api/pdInheritanceTest")
public class PeripheralDeviceInheritanceTestController implements IPeripheralDeviceInheritanceTestService {

    @Autowired
    PeripheralDeviceInheritanceTestService peripheralDeviceInheritanceTestService;

    @GetMapping(path = "/in/{id}", produces = "application/json")
    public PeripheralDeviceInDTO findInById(@PathVariable("id") Long idPeripheral) throws Exception {
        return peripheralDeviceInheritanceTestService.findInById(idPeripheral);
    }

    @PostMapping(path = "", produces = "application/json")
    @ResponseBody
    public PeripheralDeviceInDTO testDeviceAsInParameter(@RequestBody PeripheralDeviceInDTO peripheralDeviceInDTO) throws Exception {
        return peripheralDeviceInheritanceTestService.testDeviceAsInParameter(peripheralDeviceInDTO);
    }

    @GetMapping(path = "/out/{id}", produces = "application/json")
    public PeripheralDeviceOutDTO findOutById(@PathVariable("id")Long idPeripheral) throws Exception {
        return peripheralDeviceInheritanceTestService.findOutById(idPeripheral);
    }
}

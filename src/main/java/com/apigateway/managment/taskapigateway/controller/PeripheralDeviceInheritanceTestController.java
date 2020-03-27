package com.apigateway.managment.taskapigateway.controller;

import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceInDTO;
import com.apigateway.managment.taskapigateway.dto.PeripheralDeviceOutDTO;
import com.apigateway.managment.taskapigateway.service.IPeripheralDeviceInheritanceTestService;
import com.apigateway.managment.taskapigateway.service.implementation.PeripheralDeviceInheritanceTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Description;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@Description(value = "")
@RequestMapping(path = "/api/pdInheritanceTest")
public class PeripheralDeviceInheritanceTestController implements IPeripheralDeviceInheritanceTestService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    PeripheralDeviceInheritanceTestService peripheralDeviceInheritanceTestService;

    @GetMapping(path = "/in/{id}", produces = "application/json")
    public PeripheralDeviceInDTO findInById(@PathVariable("id") Long idPeripheral) throws Exception {
        return peripheralDeviceInheritanceTestService.findInById(idPeripheral);
    }

    @GetMapping(path = "/locale/{tag}", produces = "application/json")
    public String getLocaleMessage(@PathVariable("tag") String tag) throws Exception {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(tag));
        String[] params = new String[]{"Giselle Yanet"};
        return getLocale(params);
    }

    private String getLocale(String[] params) {
        return messageSource.getMessage("label.welcome", params, LocaleContextHolder.getLocale());
    }

    @GetMapping(path = "/locale", produces = "application/json")
    public String getLocaleMessage() throws Exception {
        String[] params = new String[]{"Giselle Yanet"};
        return getLocale(params);
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

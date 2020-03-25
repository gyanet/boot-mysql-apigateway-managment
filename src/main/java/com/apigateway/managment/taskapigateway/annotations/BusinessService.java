package com.apigateway.managment.taskapigateway.annotations;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;
import java.util.Date;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Service
public @interface BusinessService {
}

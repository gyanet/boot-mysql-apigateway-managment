package com.apigateway.managment.taskapigateway.annotations;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MethodInfo {
    String author() default "Giselle Yanet";
    String comments();
    String date();
    String expireDate();
    int revision() default 1;

}

package com.apigateway.managment.taskapigateway.aop;

import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GatewayServiceAspect {

    @Around("@annotation(com.apigateway.managment.taskapigateway.annotations.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    Long executionTime = System.currentTimeMillis() - start;

    System.out.println(joinPoint.getSignature() + " executed in " + executionTime + " miliseconds.");
    return proceed;
    }

}

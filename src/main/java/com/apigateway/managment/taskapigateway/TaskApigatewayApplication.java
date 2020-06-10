package com.apigateway.managment.taskapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class TaskApigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApigatewayApplication.class, args);
	}

}


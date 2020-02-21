package com.apigateway.managment.taskapigateway.error.ex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomException extends RuntimeException {

    private String message;
    private String details;
    private String hint;
    private String nextActions;
    private String support;
}
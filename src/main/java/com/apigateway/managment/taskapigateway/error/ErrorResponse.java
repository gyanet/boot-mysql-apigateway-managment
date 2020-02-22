package com.apigateway.managment.taskapigateway.error;

import com.apigateway.managment.taskapigateway.utils.ResponseType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorResponse {
    private ResponseType type;
    private String message;
    private String path;
    private Date date = new Date();

}

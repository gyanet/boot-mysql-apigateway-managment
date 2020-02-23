package com.apigateway.managment.taskapigateway.error;

import com.apigateway.managment.taskapigateway.utils.ResponseType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    private ResponseType type;
    private String message;
    private String debugMessage;
    private List<SubError> details;
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date = new Date();

}

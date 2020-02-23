package com.apigateway.managment.taskapigateway.error;

import com.apigateway.managment.taskapigateway.utils.ResponseType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    private String path;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date date = new Date();
    private List<SubError> subErrors;

}

package com.apigateway.managment.taskapigateway.error;

import com.apigateway.managment.taskapigateway.error.ex.GatewayException;
import com.apigateway.managment.taskapigateway.error.ex.GatewayNotFoundException;
import com.apigateway.managment.taskapigateway.utils.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
class ErrorHandlingInterceptor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GatewayException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorResponse handleResourceNotFound(final GatewayException exception,
                                       final HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        error.setType(ResponseType.ERROR);
        error.setDate(new Date());

        return error;
    }

}
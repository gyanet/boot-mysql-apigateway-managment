package com.apigateway.managment.taskapigateway.error;

import com.apigateway.managment.taskapigateway.error.ex.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
class ApiExceptionsHandlingInterceptor extends ResponseEntityExceptionHandler {

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        return buildResponseEntity(new ApiErrorResponse(BAD_REQUEST, error, ex));
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), ex));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiErrorResponse apiError = new ApiErrorResponse(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle GatewayException. This one triggers when JSON is invalid as well.
     *
     * @param ex      GatewayException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(GatewayException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ApiErrorResponse handleResourceNotFound(final GatewayException exception,
                                            final HttpServletRequest request) {
        return getApiErrorResponse(exception, request);
    }

    /**
     * Handle PeripheralDeviceException. This one triggers when JSON is invalid as well.
     *
     * @param ex      GatewayException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(PeripheralDeviceException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ApiErrorResponse handleResourceNotFound(final PeripheralDeviceException exception,
                                            final HttpServletRequest request) {
        return getApiErrorResponse(exception, request);
    }

    /**
     * Handle GatewayNotFoundException. This one triggers when JSON is invalid as well.
     *
     * @param ex      GatewayNotFoundException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(GatewayNotFoundException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ApiErrorResponse handleResourceNotFound(final GatewayNotFoundException exception,
                                            final HttpServletRequest request) {
        return getApiErrorResponse(exception, request);
    }

    /**
     * Handle PeripheralDeviceNotFoundException. This one triggers when JSON is invalid as well.
     *
     * @param ex      PeripheralDeviceNotFoundException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(PeripheralDeviceNotFoundException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ApiErrorResponse handleResourceNotFound(final PeripheralDeviceNotFoundException exception,
                                            final HttpServletRequest request) {
        return getApiErrorResponse(exception, request);
    }

    /**
     * Handle GatewayDataValidationException. This one triggers when JSON is invalid as well.
     *
     * @param ex      GatewayDataValidationException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(GatewayDataValidationException.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public @ResponseBody
    ApiErrorResponse handleResourceNotFound(final GatewayDataValidationException exception,
                                            final HttpServletRequest request) {
        return getApiErrorResponse(exception, request);
    }

    private ApiErrorResponse getApiErrorResponse(Exception exception, HttpServletRequest request) {
        ApiErrorResponse error = new ApiErrorResponse();
        error.setMessage(exception.getMessage());
        error.setMethod(request.getMethod());
        error.setPath(request.getRequestURI());
        error.setStatus(INTERNAL_SERVER_ERROR);
        error.setDate(new Date());

        return error;
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(BAD_REQUEST);
        apiError.setMessage("Validation error");
        apiError.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiErrorResponse(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildResponseEntity(new ApiErrorResponse(INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiErrorResponse object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        ApiErrorResponse apiError = new ApiErrorResponse(BAD_REQUEST);
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setDebugMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
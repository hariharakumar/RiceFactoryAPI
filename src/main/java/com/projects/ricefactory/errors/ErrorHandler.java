package com.projects.ricefactory.errors;

import com.projects.ricefactory.api.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static com.projects.ricefactory.errors.ErrorCode.UNKOWN_ERROR;

@ControllerAdvice
@EnableWebMvc
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ErrorDetail<Map> ed = new ErrorDetail<>(ErrorCode.UNSUPPORTED_METHOD, "Unsupported Method");
        return new ResponseEntity<>(new ErrorDetails(ed), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ErrorDetail<Map> ed = new ErrorDetail<>(ErrorCode.UNSUPPORTED_MEDIA_TYPE,"Unsupported Media Type");
        return new ResponseEntity<>(new ErrorDetails(ed), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        return handleNotFoundException(new NotFoundException("Page not found", "path", path, ex), request);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorDetails(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public final ResponseEntity<ErrorDetails> handleNotAuthorizedExceotion(NotAuthorizedException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorDetails(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<ErrorDetails> handleValidationException(ValidationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorDetails(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorDetails> handleGenericException(ApiException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorDetails(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleUnknownException(Exception ex, WebRequest request) {
        logger.error("request: " + request + " threw unhandled exception: ", ex);
        ErrorDetails ed = new ErrorDetails(null, UNKOWN_ERROR, "Unknown error occurred", null);
        return new ResponseEntity<>(ed, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

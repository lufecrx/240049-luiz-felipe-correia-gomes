package br.com.lufecrx.crudexercise.exceptions.global.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import br.com.lufecrx.crudexercise.exceptions.global.message.RestErrorMessage;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,
            WebRequest request) {
        
        // Get the field errors and put them in a map
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        
        // Error message format: {field1=message1, field2=message2, ...}
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("{");
        errors.forEach((key, value) -> errorMessage.append(key).append("=").append(value).append(", "));
        if (errorMessage.length() > 1) { // Verify if there is any error message
            errorMessage.setLength(errorMessage.length() - 2); // Remove the last comma and space ", "
        }
        errorMessage.append("}");
        
        // Create the response entity with the error message
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleConstraintViolationException(ConstraintViolationException ex,
            WebRequest request) {

        // Get the constraint violations and put them in a map
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations()
                .forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));

        // Error message format: {field1=message1, field2=message2, ...}
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("{");
        errors.forEach((key, value) -> errorMessage.append(key).append("=").append(value).append(", "));
        if (errorMessage.length() > 1) { // Verify if there is any error message
            errorMessage.setLength(errorMessage.length() - 2); // Remove the last comma and space ", "
        }
        errorMessage.append("}");

        // Create the response entity with the error message
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, errorMessage.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)   
    public ResponseEntity<RestErrorMessage> handleResponseStatusException(ResponseStatusException ex) {
        HttpStatusCode httpStatusCode = ex.getStatusCode();
        HttpStatus httpStatus = HttpStatus.valueOf(httpStatusCode.value());
        
        // Create the response entity with the error message
        RestErrorMessage threatResponse = new RestErrorMessage(httpStatus, ex.getReason());
        return ResponseEntity.status(httpStatus).body(threatResponse);
    }

}
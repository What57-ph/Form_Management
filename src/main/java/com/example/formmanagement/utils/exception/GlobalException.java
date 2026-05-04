package com.example.formmanagement.utils.exception;

import com.example.formmanagement.domain.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {
            ExistException.class,
            FieldValidationException.class,
            RequestInvalidException.class
    })
    public ResponseEntity<RestResponse<Object>> handleBadRequestException(Exception e) {
        return ResponseEntity.badRequest().body(
                RestResponse.builder()
                        .data(null)
                        .error(e.getMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(null)
                        .build()
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<RestResponse<Object>> handleNotFoundCase(Exception e) {
        return ResponseEntity.badRequest().body(
                RestResponse.builder()
                        .data(null)
                        .error(e.getMessage())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(null)
                        .build()
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse<Object>> handleNotDefinedException(Exception e) {
        return ResponseEntity.internalServerError().body(
                RestResponse.builder()
                        .data(null)
                        .error("Internal server error")
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(null)
                        .build()
        );
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<RestResponse<Object>> handleValidation(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(
                RestResponse.builder()
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Validation failed")
                        .error(errors.toString().split("=")[1].replace("}",""))
                        .data(null)
                        .build()
        );
    }
}

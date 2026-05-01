package com.example.formmanagement.utils.exception;

import com.example.formmanagement.domain.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = {
            ExistException.class,
            FieldValidationException.class,
            NotFoundException.class,
            RequestInvalidException.class
    })
    public ResponseEntity<RestResponse<Object>> handleBadRequestException(Exception e){
        return ResponseEntity.badRequest().body(
                RestResponse.builder()
                        .data(null)
                        .error(e.getMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message(null)
                        .build()
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse<Object>> handleNotDefinedException(Exception e){
        return ResponseEntity.internalServerError().body(
                RestResponse.builder()
                        .data(null)
                        .error(e.getMessage())
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(null)
                        .build()
        );
    }
}

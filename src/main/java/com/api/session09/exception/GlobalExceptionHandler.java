package com.api.session09.exception;

import com.api.session09.dto.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> result = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField
                        , DefaultMessageSourceResolvable::getDefaultMessage
                ));
        ApiResponse apiResponse = ApiResponse.builder()
                .status("FAIL")
                .message("Invalid input data")
                .data(result)
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }

}

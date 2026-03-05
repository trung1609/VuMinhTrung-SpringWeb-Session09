package com.api.session09.exception;

import com.api.session09.dto.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> result = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField
                        , DefaultMessageSourceResolvable::getDefaultMessage
                ));
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status("FAIL")
                .message("Invalid input data")
                .data(result)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status("FAIL")
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<?> handleResourceConflictException(ResourceConflictException ex){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .status("FAIL")
                .message(ex.getMessage())
                .data(null)
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }


}

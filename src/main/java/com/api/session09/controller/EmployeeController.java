package com.api.session09.controller;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.EmployeeDTO;
import com.api.session09.exception.ResourceConflictException;
import com.api.session09.exception.ResourceNotFoundException;
import com.api.session09.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(@Valid @RequestBody EmployeeDTO request) throws ResourceNotFoundException, ResourceConflictException {
        return new ResponseEntity<>(employeeService.createEmployee(request), HttpStatus.CREATED);
    }
}

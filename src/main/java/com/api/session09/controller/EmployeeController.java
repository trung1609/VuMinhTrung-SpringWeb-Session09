package com.api.session09.controller;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.EmployeeCreateDTO;
import com.api.session09.dto.EmployeeDTO;
import com.api.session09.dto.EmployeeUpdateDTO;
import com.api.session09.exception.FileStorageException;
import com.api.session09.exception.ResourceConflictException;
import com.api.session09.exception.ResourceNotFoundException;
import com.api.session09.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(@Valid @ModelAttribute EmployeeCreateDTO request) throws ResourceNotFoundException, ResourceConflictException, FileStorageException {
        return new ResponseEntity<>(employeeService.createEmployee(request), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateAvatarEmployee(@PathVariable Long id,
                                                                         @Valid @ModelAttribute EmployeeUpdateDTO request) throws FileStorageException, ResourceNotFoundException {
        return new ResponseEntity<>(employeeService.updateEmployee(id, request), HttpStatus.OK);
    }
}

package com.api.session09.service;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.EmployeeCreateDTO;
import com.api.session09.dto.EmployeeDTO;
import com.api.session09.dto.EmployeeUpdateDTO;
import com.api.session09.exception.FileStorageException;
import com.api.session09.exception.ResourceConflictException;
import com.api.session09.exception.ResourceNotFoundException;

public interface EmployeeService {
    ApiResponse<EmployeeDTO> createEmployee(EmployeeCreateDTO request) throws ResourceNotFoundException, ResourceConflictException, FileStorageException;
    ApiResponse<EmployeeDTO> updateEmployee(Long id, EmployeeUpdateDTO request) throws FileStorageException, ResourceNotFoundException;
}

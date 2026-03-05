package com.api.session09.service;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.EmployeeDTO;
import com.api.session09.exception.ResourceConflictException;
import com.api.session09.exception.ResourceNotFoundException;

public interface EmployeeService {
    ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO request) throws ResourceNotFoundException, ResourceConflictException;
}

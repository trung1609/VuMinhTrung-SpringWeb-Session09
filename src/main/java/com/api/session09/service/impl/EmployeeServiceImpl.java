package com.api.session09.service.impl;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.EmployeeDTO;
import com.api.session09.entity.Department;
import com.api.session09.entity.Employee;
import com.api.session09.exception.ResourceConflictException;
import com.api.session09.exception.ResourceNotFoundException;
import com.api.session09.repository.DepartmentRepository;
import com.api.session09.repository.EmployeeRepository;
import com.api.session09.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ApiResponse<EmployeeDTO> createEmployee(EmployeeDTO request) throws ResourceNotFoundException, ResourceConflictException {

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        if(employeeRepository.existsByEmail(request.getEmail())){
            throw new ResourceConflictException("Employee already exists with email: " + request.getEmail());
        }

        Employee employee = Employee.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .salary(request.getSalary())
                .department(department)
                .build();
        employeeRepository.save(employee);

        EmployeeDTO dto = EmployeeDTO.builder()
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartment().getId())
                .build();
        return new ApiResponse<>(
                "Success",
                "Create employee successfully",
                dto
        );
    }
}

package com.api.session09.service.impl;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.EmployeeCreateDTO;
import com.api.session09.dto.EmployeeDTO;
import com.api.session09.dto.EmployeeUpdateDTO;
import com.api.session09.entity.Department;
import com.api.session09.entity.Employee;
import com.api.session09.exception.FileStorageException;
import com.api.session09.exception.ResourceConflictException;
import com.api.session09.exception.ResourceNotFoundException;
import com.api.session09.repository.DepartmentRepository;
import com.api.session09.repository.EmployeeRepository;
import com.api.session09.service.EmployeeService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ApiResponse<EmployeeDTO> createEmployee(EmployeeCreateDTO request) throws ResourceNotFoundException, ResourceConflictException, FileStorageException {

        Department department = departmentRepository.findById(request.getDepartmentId()).orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.getDepartmentId()));

        if(employeeRepository.existsByEmail(request.getEmail())){
            throw new ResourceConflictException("Employee already exists with email: " + request.getEmail());
        }

        String imageUrl = null;

        MultipartFile file = request.getAvatarImage();

        if (file == null || file.isEmpty()) {
            throw new FileStorageException("Avatar image file is missing or empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jpg"))) {
            throw new FileStorageException("Invalid file type. Only JPEG, PNG, and JPG are allowed.");
        }

        String fileName = file.getOriginalFilename();
        if(fileName == null || !fileName.toLowerCase().matches("^.*\\.(jpg|jpeg|png)$")){
            throw new FileStorageException("Invalid file extension.");
        }

        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            imageUrl = (String) result.get("url");
        }catch (IOException e) {
            throw new FileStorageException("Failed to upload avatar image");
        }

        Employee employee = Employee.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .salary(request.getSalary())
                .department(department)
                .avatarUrl(imageUrl)
                .build();
        employeeRepository.save(employee);

        EmployeeDTO dto = EmployeeDTO.builder()
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartment().getId())
                .avatarUrl(employee.getAvatarUrl())
                .build();
        return new ApiResponse<>(
                "Success",
                "Create employee successfully",
                dto
        );
    }

    @Override
    public ApiResponse<EmployeeDTO> updateEmployee(Long id, EmployeeUpdateDTO request) throws FileStorageException, ResourceNotFoundException {
        String imageUrl = null;

        MultipartFile file = request.getAvatarImage();

        if (file == null || file.isEmpty()) {
            throw new FileStorageException("Avatar image file is missing or empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !(contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("image/jpg"))) {
            throw new FileStorageException("Invalid file type. Only JPEG, PNG, and JPG are allowed.");
        }

        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            imageUrl = (String) result.get("url");
        }catch (IOException e) {
            throw new FileStorageException("Failed to upload avatar image");
        }
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        employee.setAvatarUrl(imageUrl);

        employeeRepository.save(employee);

        EmployeeDTO dto = EmployeeDTO.builder()
                .avatarUrl(employee.getAvatarUrl())
                .build();
        return new ApiResponse<>(
                "Success",
                "Update employee successfully",
                dto
        );
    }
}

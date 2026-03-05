package com.api.session09.service.impl;

import com.api.session09.dto.ApiResponse;
import com.api.session09.dto.DepartmentDTO;
import com.api.session09.entity.Department;
import com.api.session09.repository.DepartmentRepository;
import com.api.session09.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public ApiResponse<DepartmentDTO> createDepartment(DepartmentDTO request) {
        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
        departmentRepository.save(department);

        DepartmentDTO response = DepartmentDTO.builder()
                .name(department.getName())
                .description(department.getDescription())
                .build();
        return new ApiResponse<>(
                "Success",
                "Create department successfully",
                response
        );
    }
}

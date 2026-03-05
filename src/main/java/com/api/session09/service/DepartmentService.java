package com.api.session09.service;

import com.api.session09.dto.DepartmentDTO;
import com.api.session09.entity.Department;

public interface DepartmentService {
    Department createDepartment(DepartmentDTO request);
}

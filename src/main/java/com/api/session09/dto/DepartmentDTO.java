package com.api.session09.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDTO {

    @NotBlank(message = "Department name cannot be empty")
    @Size(min = 5, max = 50, message = "Department name must be between 5 and 50 characters")
    private String name;

    @Size(max = 100, message = "Description must be less than 100 characters")
    private String description;
}

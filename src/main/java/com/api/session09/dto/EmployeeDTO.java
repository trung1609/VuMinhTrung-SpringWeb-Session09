package com.api.session09.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    @NotBlank(message = "Full name cannot be empty")
    private String fullName;

    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^0([35678])\\d{8}$", message = "Phone number is not valid")
    private String phone;

    @NotNull(message = "Salary cannot be null")
    @Min(value = 5000000, message = "Salary must be at least 5000000")
    private BigDecimal salary;

    @NotNull(message = "Department ID cannot be null")
    private Long departmentId;

    private String avatarUrl;
}

package com.api.session09.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApiResponse {
    private String status;
    private String message;
    private Object data;
}

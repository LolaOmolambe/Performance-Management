package com.appraisal.modules.employee.apimodels.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeModel {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime dateEmployed;
}

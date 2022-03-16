package com.appraisal.modules.employee.apimodels.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssignEmployeeToManagerModel {
    @NotNull(message = "Manager Id is required.")
    private Long managerId;

    @NotNull(message = "Employee Id is required.")
    private Long employeeId;
}

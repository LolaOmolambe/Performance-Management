package com.appraisal.modules.manager.apimodels.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddManagerModel {

    @NotNull(message = "Employee Id is required.")
    private Long employeeId;
}

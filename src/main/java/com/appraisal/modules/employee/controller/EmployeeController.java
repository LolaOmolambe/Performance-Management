package com.appraisal.modules.employee.controller;

import com.appraisal.common.annotations.GenericSuccessResponse;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@GenericSuccessResponse
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeModel addEmployee(@Validated @RequestBody AddEmployeeModel addEmployeeModel) {
        return employeeService.addEmployee(addEmployeeModel);
    }
}

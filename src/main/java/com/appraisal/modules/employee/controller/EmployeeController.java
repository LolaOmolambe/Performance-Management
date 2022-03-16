package com.appraisal.modules.employee.controller;

import com.appraisal.common.annotations.GenericSuccessResponse;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.request.AssignEmployeeToManagerModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.DefaultEmployeeManagerService;
import com.appraisal.modules.employee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@GenericSuccessResponse
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;
    private final DefaultEmployeeManagerService defaultEmployeeManagerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeModel addEmployee(@Validated @RequestBody AddEmployeeModel addEmployeeModel) {
        return employeeService.addEmployee(addEmployeeModel);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeModel getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeModel> getEmployees(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return employeeService.getEmployees(page, size);
    }

    @PostMapping("/assign-manager")
    @ResponseStatus(HttpStatus.OK)
    public void assignEmployeeToManager(@Validated @RequestBody AssignEmployeeToManagerModel employeeToManagerModel) {
         defaultEmployeeManagerService.assignEmployeeToManager(employeeToManagerModel.getEmployeeId(), employeeToManagerModel.getManagerId());
    }
}

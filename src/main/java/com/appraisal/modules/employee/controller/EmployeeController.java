package com.appraisal.modules.employee.controller;

import com.appraisal.common.annotations.GenericSuccessResponse;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.request.AssignEmployeeToManagerModel;
import com.appraisal.modules.employee.apimodels.request.UpdateEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.DefaultEmployeeManagerService;
import com.appraisal.modules.employee.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public EmployeeModel getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping
    public List<EmployeeModel> getEmployees(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return employeeService.getEmployees(pageable);
    }

    @PostMapping("/assign-manager")
    public void assignEmployeeToManager(@Validated @RequestBody AssignEmployeeToManagerModel employeeToManagerModel) {
         defaultEmployeeManagerService.assignEmployeeToManager(employeeToManagerModel.getEmployeeId(), employeeToManagerModel.getManagerId());
    }

    @PutMapping(value = "/{id}")
    public EmployeeModel updateEmployee(@PathVariable(value = "id") Long id,
                                        @Validated @RequestBody UpdateEmployeeModel updateEmployeeModel){
       return employeeService.updateEmployeeDetails(id, updateEmployeeModel);
    }
}

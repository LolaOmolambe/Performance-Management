package com.appraisal.modules.manager.controller;

import com.appraisal.common.annotations.GenericSuccessResponse;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.manager.apimodels.request.AddManagerModel;
import com.appraisal.modules.manager.services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@GenericSuccessResponse
@RequestMapping("/api/v1/managers")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addManager(@Validated @RequestBody AddManagerModel addManagerModel){
        managerService.addManager(addManagerModel.getEmployeeId());
    }

    @GetMapping(path = "/{id}")
    public EmployeeModel getManager(@PathVariable Long id) {
        return managerService.getManager(id);
    }

    @GetMapping
    public List<EmployeeModel> getManagers(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return managerService.getManagers(pageable);
    }

    @GetMapping(path = "/{id}/employees")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeModel> getEmployeesAttachedToManager(@PathVariable Long id, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return managerService.getEmployeesAttachedToManager(id, pageable);
    }
}

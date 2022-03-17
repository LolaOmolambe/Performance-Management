package com.appraisal.modules.manager.controller;

import com.appraisal.common.annotations.GenericSuccessResponse;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.manager.apimodels.request.AddManagerModel;
import com.appraisal.modules.manager.services.ManagerService;
import lombok.RequiredArgsConstructor;
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
    @ResponseStatus(HttpStatus.OK)
    public EmployeeModel getManager(@PathVariable Long id) {
        return managerService.getManager(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeModel> getManagers(@RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        return managerService.getManagers(page, size);
    }
}

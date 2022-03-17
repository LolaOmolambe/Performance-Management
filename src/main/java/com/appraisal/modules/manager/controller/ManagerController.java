package com.appraisal.modules.manager.controller;

import com.appraisal.common.annotations.GenericSuccessResponse;
import com.appraisal.modules.manager.apimodels.request.AddManagerModel;
import com.appraisal.modules.manager.services.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
}

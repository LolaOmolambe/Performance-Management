package com.appraisal.modules.manager.services;

import com.appraisal.modules.employee.apimodels.response.EmployeeModel;

public interface ManagerService {
    void addManager(Long employeeId);

    EmployeeModel getManager(Long managerId);
}

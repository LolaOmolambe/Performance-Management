package com.appraisal.modules.employee.services;

public interface EmployeeManagerService {
    void assignEmployeeToManager(Long employeeId, Long managerId);
}

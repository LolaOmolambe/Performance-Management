package com.appraisal.modules.manager.services;

import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ManagerService {
    void addManager(Long employeeId);

    EmployeeModel getManager(Long managerId);

    List<EmployeeModel> getManagers(Pageable pageable);
}

package com.appraisal.modules.employee.services;

import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    EmployeeModel addEmployee(AddEmployeeModel employeeModel);

    EmployeeModel getEmployee(Long employeeId);

    List<EmployeeModel> getEmployees(Pageable pageable);
}

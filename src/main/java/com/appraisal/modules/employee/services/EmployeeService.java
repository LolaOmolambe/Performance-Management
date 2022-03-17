package com.appraisal.modules.employee.services;

import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;

public interface EmployeeService {
    EmployeeModel addEmployee(AddEmployeeModel employeeModel);
}

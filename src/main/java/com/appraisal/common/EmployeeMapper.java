package com.appraisal.common;

import com.appraisal.entities.Employee;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface EmployeeMapper {
    EmployeeModel employeeToEmployeeModel(Employee employee);

    @Mapping(target = "dateEmployed", expression = "java(addEmployeeModel.getDateEmployed().atStartOfDay())")
    Employee addEmployeeModelToEmployee(AddEmployeeModel addEmployeeModel);

    List<EmployeeModel> employeesToEmployeeModels(List<Employee> employees);
}

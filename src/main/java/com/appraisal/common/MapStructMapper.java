package com.appraisal.common;

import com.appraisal.entities.Employee;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface MapStructMapper {
    EmployeeModel employeeToEmployeeModel(Employee employee);

    @Mapping(target = "dateEmployed", expression = "java(addEmployeeModel.getDateEmployed().atStartOfDay())")
    Employee addEmployeeModelToEmployee(AddEmployeeModel addEmployeeModel);
}

package com.appraisal.modules.employee.services.impl;

import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.entities.Employee;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.EmployeeManagerService;
import com.appraisal.modules.employee.services.EmployeeService;
import com.appraisal.modules.user.services.UserService;
import com.appraisal.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final EmployeeManagerService employeeManagerService;

    @Override
    @Transactional
    public EmployeeModel addEmployee(AddEmployeeModel employeeModel) {

        boolean userExists = userService.userExists(employeeModel.getEmailAddress());

        if (userExists) {
            throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
        }

        employeeRepository.findEmployeeByEmailAddress(employeeModel.getEmailAddress())
                .ifPresent(employee -> {
                    throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
                });

        Employee employee = saveEmployee(employeeModel);

        if (Objects.nonNull(employeeModel.getManagerId())) {
            employeeManagerService.assignEmployeeToManager(employee.getId(), employeeModel.getManagerId());
        }

        return EmployeeModel.builder().dateEmployed(employee.getDateEmployed())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .emailAddress(employee.getEmailAddress())
                .build();
    }

    private Employee saveEmployee(AddEmployeeModel employeeModel) {
        Employee employee = Employee.builder()
                .firstName(employeeModel.getFirstName())
                .lastName(employeeModel.getLastName())
                .dateEmployed(employeeModel.getDateEmployed().atStartOfDay())
                .emailAddress(employeeModel.getEmailAddress())
                .build();

        return employeeRepository.save(employee);
    }
}

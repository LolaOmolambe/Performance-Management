package com.appraisal.modules.employee.services.impl;

import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.entities.Employee;
import com.appraisal.entities.EmployeeManager;
import com.appraisal.entities.Manager;
import com.appraisal.modules.employee.services.EmployeeManagerService;
import com.appraisal.repositories.EmployeeManagerRepository;
import com.appraisal.repositories.EmployeeRepository;
import com.appraisal.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeManagerServiceImpl implements EmployeeManagerService {
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final EmployeeManagerRepository employeeManagerRepository;

    @Override
    public void assignEmployeeToManager(Long employeeId, Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.INVALID_MANAGER));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.INVALID_EMPLOYEE));

        boolean isEmployeeAssignedToAManager = employeeManagerRepository.existsByEmployee(employee);

        if (isEmployeeAssignedToAManager) {
            throw new BadRequestException(ResponseCode.EMPLOYEE_MANAGER_EXISTS);
        }

        EmployeeManager employeeManager = EmployeeManager.builder().employee(employee).manager(manager).build();

        employeeManagerRepository.save(employeeManager);
    }
}

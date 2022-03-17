package com.appraisal.modules.manager.services.impl;

import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.entities.Employee;
import com.appraisal.entities.Manager;
import com.appraisal.modules.manager.services.ManagerService;
import com.appraisal.repositories.EmployeeRepository;
import com.appraisal.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;

    @Override
    public void addManager(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new BadRequestException(ResponseCode.INVALID_EMPLOYEE));

        boolean managerExists = managerRepository.existsByEmployee(employee);

        if (managerExists) {
            throw new BadRequestException(ResponseCode.MANAGER_EXISTS);
        }

        Manager manager = Manager.builder().employee(employee).build();

        managerRepository.save(manager);
    }
}

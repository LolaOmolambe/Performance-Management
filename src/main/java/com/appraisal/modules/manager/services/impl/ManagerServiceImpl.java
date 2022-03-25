package com.appraisal.modules.manager.services.impl;

import com.appraisal.common.EmployeeMapper;
import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.entities.Employee;
import com.appraisal.entities.Manager;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.manager.services.ManagerService;
import com.appraisal.repositories.EmployeeRepository;
import com.appraisal.repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final EmployeeRepository employeeRepository;
    private final ManagerRepository managerRepository;
    private final EmployeeMapper employeeMapper;

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

    @Override
    public EmployeeModel getManager(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.INVALID_MANAGER));

        return employeeMapper.employeeToEmployeeModel(manager.getEmployee());
    }

    @Override
    public List<EmployeeModel> getManagers(Pageable pageable) {
        Page<Manager> managers = managerRepository.findAll(pageable);
        List<Manager> managersContent = managers.getContent();

        return getEmployeeModels(managersContent);
    }

    private List<EmployeeModel> getEmployeeModels(List<Manager> managersContent) {
        return managersContent.stream()
                .map(this::getEmployeeModel).toList();
    }

    private EmployeeModel getEmployeeModel(Manager manager) {
        Employee employee = manager.getEmployee();
        return EmployeeModel.builder()
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .dateEmployed(employee.getDateEmployed())
                .id(employee.getId())
                .email(employee.getEmail())
                .build();
    }
}

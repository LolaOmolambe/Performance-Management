package com.appraisal.modules.employee.services.impl;

import com.appraisal.common.MapStructMapper;
import com.appraisal.common.enums.ResponseCode;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.entities.Employee;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.DefaultEmployeeManagerService;
import com.appraisal.modules.employee.services.EmployeeService;
import com.appraisal.modules.user.services.UserService;
import com.appraisal.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final UserService userService;
    private final DefaultEmployeeManagerService employeeManagerService;
    private final MapStructMapper mapStructMapper;

    @Override
    @Transactional
    public EmployeeModel addEmployee(AddEmployeeModel employeeModel) {

        boolean userExists = userService.userExists(employeeModel.getEmail());

        if (userExists) {
            throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
        }

        employeeRepository.findEmployeeByEmail(employeeModel.getEmail())
                .ifPresent(employee -> {
                    throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
                });

        boolean employeeExists = employeeRepository.existsByEmail(employeeModel.getEmail());

        if (employeeExists) {
            throw new BadRequestException(ResponseCode.DUPLICATE_EMAIL);
        }

        Employee employee = saveEmployee(employeeModel);

        if (Objects.nonNull(employeeModel.getManagerId())) {
            employeeManagerService.assignEmployeeToManager(employee.getId(), employeeModel.getManagerId());
        }

        return mapStructMapper.employeeToEmployeeModel(employee);
    }

    @Override
    public EmployeeModel getEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NotFoundException(ResponseCode.INVALID_EMPLOYEE));

        return mapStructMapper.employeeToEmployeeModel(employee);
    }

    @Override
    public List<EmployeeModel> getEmployees(int page, int pageSize) {
        if (page > 0) page = page - 1;

        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize));
        List<Employee> employeeList = employees.getContent();

        return mapStructMapper.map(employeeList);
    }

    private Employee saveEmployee(AddEmployeeModel employeeModel) {
        Employee employee = mapStructMapper.addEmployeeModelToEmployee(employeeModel);
        return employeeRepository.save(employee);
    }
}

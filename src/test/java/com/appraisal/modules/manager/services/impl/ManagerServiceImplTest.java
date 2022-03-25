package com.appraisal.modules.manager.services.impl;

import com.appraisal.TestData;
import com.appraisal.common.EmployeeMapper;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.entities.Employee;
import com.appraisal.entities.Manager;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.repositories.EmployeeRepository;
import com.appraisal.repositories.ManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private ManagerServiceImpl managerService;

    private Employee employee;
    private Manager manager;
    private EmployeeModel employeeModel;

    @BeforeEach
    public void setUp() {
        employee = TestData.generateEmployee();
        manager = TestData.generateManager();
        employeeModel = TestData.generateEmployeeModel();
    }

    @Test
    public void addManagerFails_whenEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.addManager(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Employee does not exist.");
    }

    @Test
    public void addManagerFails_whenManagerAlreadyExists() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        when(managerRepository.existsByEmployee(employee))
                .thenReturn(true);

        assertThatThrownBy(() -> managerService.addManager(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("A manager with this email already exists.");
    }

    @Test
    public void addManagerSuccessfully() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        when(managerRepository.existsByEmployee(employee))
                .thenReturn(false);

        assertDoesNotThrow(() -> managerService.addManager(1L));
    }

    @Test
    public void getManagerFails_whenManagerDoesNotExists() {
        when(managerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> managerService.getManager(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Manager does not exist.");
    }

    @Test
    public void getManagerSuccessfully() {
        when(managerRepository.findById(1L))
                .thenReturn(Optional.of(manager));
        when(employeeMapper.employeeToEmployeeModel(employee))
                .thenReturn(employeeModel);

        EmployeeModel manager = managerService.getManager(1L);

        assertNotNull(manager.getEmail());
        assertNotNull(manager.getFirstName());
        assertNotNull(manager.getLastName());
    }
}
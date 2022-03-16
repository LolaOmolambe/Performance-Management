package com.appraisal.modules.employee.services.impl;

import com.appraisal.TestData;
import com.appraisal.entities.Employee;
import com.appraisal.entities.Manager;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.repositories.EmployeeManagerRepository;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeManagerServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private EmployeeManagerRepository employeeManagerRepository;

    @InjectMocks
    private EmployeeManagerServiceImpl employeeManagerService;

    private Manager manager;
    private Employee employee;

    @BeforeEach
    void setUp() {
        manager = TestData.generateManager();
        employee = TestData.generateEmployee();
    }


    @Test
    public void assignEmployeeToManagerFails_whenManagerDoesNotExists() {
        when(managerRepository.findById(2L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeManagerService.assignEmployeeToManager(1L, 2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Manager does not exist.");
    }

    @Test
    public void assignEmployeeToManagerFails_whenEmployeeDoesNotExists() {
        when(managerRepository.findById(2L))
                .thenReturn(Optional.of(manager));
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeManagerService.assignEmployeeToManager(1L, 2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Employee does not exist.");
    }

    @Test
    public void assignEmployeeToManagerFails_whenEmployeeIsAlreadyAttachedToAManager() {
        when(managerRepository.findById(2L))
                .thenReturn(Optional.of(manager));
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        when(employeeManagerRepository.existsByEmployee(employee))
                .thenReturn(true);

        assertThatThrownBy(() -> employeeManagerService.assignEmployeeToManager(1L, 2L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Employee already assigned to a manager.");
    }

    @Test
    public void shouldAssignEmployeeToManagerSuccessfully() {
        when(managerRepository.findById(2L))
                .thenReturn(Optional.of(manager));
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        when(employeeManagerRepository.existsByEmployee(employee))
                .thenReturn(false);

        assertDoesNotThrow(() -> employeeManagerService.assignEmployeeToManager(1L, 2L));
    }

}
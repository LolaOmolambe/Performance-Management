package com.appraisal.modules.employee.services.impl;

import com.appraisal.TestData;
import com.appraisal.entities.Employee;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.EmployeeManagerService;
import com.appraisal.modules.user.services.UserService;
import com.appraisal.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeManagerService employeeManagerService;

    @Mock
    private UserService userService;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private AddEmployeeModel employeeModel;
    private AddEmployeeModel employeeModelWithManager;
    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = TestData.generateEmployee();
        employeeModel = TestData.generateEmployeeModelRequest();
        employeeModelWithManager = TestData.generateEmployeeModelRequestWithManager();
    }


    @Test
    public void addEmployeeFails_whenUserEmailAlreadyExists() {
        when(userService.userExists("test@test.com"))
                .thenReturn(true);

        assertThatThrownBy(() -> employeeService.addEmployee(employeeModel))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("An employee with this email already exists.");
    }

    @Test
    public void addEmployeeFails_whenEmployeeEmailAlreadyExists() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.findEmployeeByEmailAddress("test@test.com"))
                .thenReturn(Optional.of(employee));

        assertThatThrownBy(() -> employeeService.addEmployee(employeeModel))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("An employee with this email already exists.");
    }

    @Test
    public void shouldAddEmployeeSuccessfully() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.findEmployeeByEmailAddress("test@test.com"))
                .thenReturn(Optional.empty());
        when(employeeRepository.save(employee))
                .thenReturn(employee);

        EmployeeModel savedEmployee = employeeService.addEmployee(employeeModel);

        assertEquals(employeeModel.getEmailAddress(), savedEmployee.getEmailAddress());
        assertEquals(employeeModel.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employeeModel.getLastName(), savedEmployee.getLastName());
    }

    @Test
    public void addEmployeeWithManagerFails_whenManagerDoesNotExists() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.findEmployeeByEmailAddress("test@test.com"))
                .thenReturn(Optional.empty());
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        doThrow(new NotFoundException("Manager does not exist.")).
                when(employeeManagerService).assignEmployeeToManager(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());

        assertThatThrownBy(() -> employeeService.addEmployee(employeeModelWithManager))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Manager does not exist.");
    }


    @Test
    public void addEmployeeWithManagerFails_whenEmployeeIsAlreadyAttachedToAManager() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.findEmployeeByEmailAddress("test@test.com"))
                .thenReturn(Optional.empty());
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        doThrow(new BadRequestException("Employee already assigned to a manager.")).
                when(employeeManagerService).assignEmployeeToManager(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());

        assertThatThrownBy(() -> employeeService.addEmployee(employeeModelWithManager))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Employee already assigned to a manager.");
    }

    @Test
    public void shouldAddEmployeeWithManagerSuccessfully() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.findEmployeeByEmailAddress("test@test.com"))
                .thenReturn(Optional.empty());
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        doNothing().
                when(employeeManagerService).assignEmployeeToManager(employee.getId(), employeeModelWithManager.getManagerId());

        EmployeeModel savedEmployee = employeeService.addEmployee(employeeModelWithManager);

        assertEquals(employeeModel.getEmailAddress(), savedEmployee.getEmailAddress());
        assertEquals(employeeModel.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employeeModel.getLastName(), savedEmployee.getLastName());
    }
}
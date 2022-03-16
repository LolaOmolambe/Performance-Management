package com.appraisal.modules.employee.services.impl;

import com.appraisal.TestData;
import com.appraisal.common.MapStructMapper;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.entities.Employee;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.response.EmployeeModel;
import com.appraisal.modules.employee.services.DefaultEmployeeManagerService;
import com.appraisal.modules.user.services.UserService;
import com.appraisal.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DefaultEmployeeManagerService employeeManagerService;

    @Mock
    private UserService userService;

    @Mock
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private AddEmployeeModel employeeModelRequest;
    private AddEmployeeModel employeeModelWithManager;
    private Employee employee;
    private EmployeeModel employeeModel;

    @BeforeEach
    void setUp() {
        employee = TestData.generateEmployee();
        employeeModel = TestData.generateEmployeeModel();
        employeeModelRequest = TestData.generateEmployeeModelRequest();
        employeeModelWithManager = TestData.generateEmployeeModelRequestWithManager();
    }


    @Test
    public void addEmployeeFails_whenUserEmailAlreadyExists() {
        when(userService.userExists("test@test.com"))
                .thenReturn(true);

        assertThatThrownBy(() -> employeeService.addEmployee(employeeModelRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("An employee with this email already exists.");
    }

    @Test
    public void addEmployeeFails_whenEmployeeEmailAlreadyExists() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.existsByEmail("test@test.com"))
                .thenReturn(true);

        assertThatThrownBy(() -> employeeService.addEmployee(employeeModelRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("An employee with this email already exists.");
    }

    @Test
    public void shouldAddEmployeeSuccessfully() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.existsByEmail("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        when(mapStructMapper.addEmployeeModelToEmployee(employeeModelRequest))
                .thenReturn(employee);
        when(mapStructMapper.employeeToEmployeeModel(employee))
                .thenReturn(employeeModel);

        EmployeeModel savedEmployee = employeeService.addEmployee(employeeModelRequest);

        assertEquals(employeeModelRequest.getEmail(), savedEmployee.getEmail());
        assertEquals(employeeModelRequest.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employeeModelRequest.getLastName(), savedEmployee.getLastName());
    }

    @Test
    public void shouldAddEmployeeWithManagerSuccessfully() {
        when(userService.userExists("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.existsByEmail("test@test.com"))
                .thenReturn(false);
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        when(mapStructMapper.addEmployeeModelToEmployee(employeeModelWithManager))
                .thenReturn(employee);
        when(mapStructMapper.employeeToEmployeeModel(employee))
                .thenReturn(employeeModel);
        doNothing().
                when(employeeManagerService).assignEmployeeToManager(employee.getId(), employeeModelWithManager.getManagerId());

        EmployeeModel savedEmployee = employeeService.addEmployee(employeeModelWithManager);

        assertEquals(employeeModelRequest.getEmail(), savedEmployee.getEmail());
        assertEquals(employeeModelRequest.getFirstName(), savedEmployee.getFirstName());
        assertEquals(employeeModelRequest.getLastName(), savedEmployee.getLastName());
    }
}
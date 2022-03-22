package com.appraisal.modules.employee.services.impl;

import com.appraisal.TestData;
import com.appraisal.common.MapStructMapper;
import com.appraisal.common.exceptions.BadRequestException;
import com.appraisal.common.exceptions.NotFoundException;
import com.appraisal.entities.Employee;
import com.appraisal.modules.employee.apimodels.request.AddEmployeeModel;
import com.appraisal.modules.employee.apimodels.request.UpdateEmployeeModel;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private UpdateEmployeeModel updateEmployeeModel;
    private PageRequest pageRequest;

    @BeforeEach
    void setUp() {
        employee = TestData.generateEmployee();
        employeeModel = TestData.generateEmployeeModel();
        employeeModelRequest = TestData.generateEmployeeModelRequest();
        employeeModelWithManager = TestData.generateEmployeeModelRequestWithManager();
        pageRequest = PageRequest.of(0, 10);
        updateEmployeeModel = TestData.generateUpdateEmployeeModelRequest();
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

    @Test
    public void getEmployeeFails_whenEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployee(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Employee does not exist.");
    }

    @Test
    public void getEmployeeSuccessfully() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        when(mapStructMapper.employeeToEmployeeModel(employee))
                .thenReturn(employeeModel);

        EmployeeModel employeeModelObj = employeeService.getEmployee(1L);

        assertNotNull(employeeModelObj.getEmail());
        assertNotNull(employeeModelObj.getFirstName());
        assertNotNull(employeeModelObj.getLastName());
    }

    @Test
    public void updateEmployeeFails_whenEmployeeDoesNotExist() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.updateEmployeeDetails(1L, updateEmployeeModel))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Employee does not exist.");
    }

    @Test
    public void updateEmployeeSuccessfully() {
        when(employeeRepository.findById(1L))
                .thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee))
                .thenReturn(employee);
        when(mapStructMapper.employeeToEmployeeModel(employee))
                .thenReturn(employeeModel);

        EmployeeModel employeeModelObj = employeeService.updateEmployeeDetails(1L, updateEmployeeModel);

        assertNotNull(employeeModelObj.getEmail());
        assertNotNull(employeeModelObj.getFirstName());
        assertNotNull(employeeModelObj.getLastName());
    }

    @Test
    public void getEmployeesSuccessfully() {
        Page<Employee> pagedResponse = TestData.getEmployees();

        when(employeeRepository.findAll(pageRequest))
                .thenReturn(pagedResponse);
        when(mapStructMapper.map(Collections.singletonList(employee)))
                .thenReturn(Collections.singletonList(employeeModel));

        List<EmployeeModel> employees = employeeService.getEmployees(pageRequest);

        assertEquals(1, employees.size());
    }


    @Test
    public void getEmptyListOfEmployeesSuccessfully() {
        when(employeeRepository.findAll(pageRequest))
                .thenReturn(Page.empty());
        when(mapStructMapper.map(Collections.emptyList()))
                .thenReturn(Collections.emptyList());

        List<EmployeeModel> employees = employeeService.getEmployees(pageRequest);

        assertEquals(0, employees.size());
    }


}
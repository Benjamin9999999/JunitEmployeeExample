package com.employee.junit;



import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepository;

    @InjectMocks
    private com.employee.junit.EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        // Optional: Clean up resources if necessary
    }

    @Test
    void getAllEmployees() {
        // Prepare a list of mock employees
        List<Employee> employees = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            employees.add(Employee.builder()
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .email("email" + i + "@example.com")
                    .build());
        }

        // Mock repository behavior
        when(employeeRepository.findAll()).thenReturn(employees);

        // Call the service method
        List<Employee> result = employeeService.getAllEmployees();

        // Verify and assert
        assertThat(result).hasSize(10);
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getEmployeeById() {
        // Mock data
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .build();

        // Mock repository behavior
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Call the service method
        Employee result = employeeService.getEmployeeById(1L);

        // Verify and assert
        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo("John");
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void saveEmployee() {
        // Prepare a single employee using the builder
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("janedoe@example.com")
                .build();

        // Mock repository behavior
        when(employeeRepository.save(employee)).thenReturn(employee);

        // Call the service method
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // Verify and assert
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getEmail()).isEqualTo("janedoe@example.com");
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void deleteEmployee() {
        // Call the service method
        employeeService.deleteEmployee(1L);

        // Verify the repository interaction
        verify(employeeRepository, times(1)).deleteById(1L);

    }
}

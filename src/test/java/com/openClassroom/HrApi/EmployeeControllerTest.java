package com.openClassroom.HrApi;

import com.openClassroom.HrApi.controller.EmployeeController;
import com.openClassroom.HrApi.model.Employee;
import com.openClassroom.HrApi.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = EmployeeControllerTest.class)
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void testGetAllEmployee() throws Exception {
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk());

    }

    @Test
    void testSaveEmployee() throws Exception {
        Employee employee = new Employee();
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employee/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John Doe\", \"password\":\"Developer\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].firstName").value("John Doe"));

        verify(employeeService, times(1)).saveEmployee(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(anyLong());

        mockMvc.perform(delete("/employee/delete/{employeeId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("employee delete"));

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setPassword("teste");
        Optional<Employee> optionalEmployee = Optional.of(employee);
        when(employeeService.getEmployeeById(anyLong())).thenReturn(optionalEmployee);

        mockMvc.perform(get("/employee/find/{employeeId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

}

package com.tannv.spring_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tannv.spring_practice.model.Employee;
import com.tannv.spring_practice.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "John Doe", 30, 60000000.0);
    }

    @Test
    void testGetAllEmployees() throws Exception {
        Mockito.when(employeeService.findAll()).thenReturn(Arrays.asList(employee));
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void testGetEmployeeById_Found() throws Exception {
        Mockito.when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetEmployeeById_NotFound() throws Exception {
        Mockito.when(employeeService.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/employees/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateEmployee_Valid() throws Exception {
        Mockito.when(employeeService.save(any(Employee.class))).thenReturn(employee);
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testCreateEmployee_Invalid() throws Exception {
        Employee invalid = new Employee(2L, "", 0, 1000.0);
        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateEmployee_Found() throws Exception {
        Mockito.when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        Mockito.when(employeeService.save(any(Employee.class))).thenReturn(employee);
        mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testUpdateEmployee_NotFound() throws Exception {
        Mockito.when(employeeService.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/employees/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteEmployee_Found() throws Exception {
        Mockito.when(employeeService.findById(1L)).thenReturn(Optional.of(employee));
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteEmployee_NotFound() throws Exception {
        Mockito.when(employeeService.findById(2L)).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/employees/2"))
                .andExpect(status().isNotFound());
    }
}

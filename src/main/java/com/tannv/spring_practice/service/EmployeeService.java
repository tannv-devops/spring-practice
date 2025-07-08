package com.tannv.spring_practice.service;

import java.util.List;
import java.util.Optional;

import com.tannv.spring_practice.model.Employee;

public interface EmployeeService {
    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    void deleteById(Long id);
}

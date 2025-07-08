package com.tannv.spring_practice.repository;

import com.tannv.spring_practice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // ...existing code or custom queries...

    // Additional query methods if needed
    @Query("SELECT e FROM Employee e WHERE e.salary > 50000000")
    List<Employee> findAllWithSalaryGreaterThan50Million();
}
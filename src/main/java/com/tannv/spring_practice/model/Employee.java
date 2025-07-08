package com.tannv.spring_practice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// generate salary field for employee
// USING lombok to generate getters and setters
// add method to validate all fields  
@Entity
@Getter
@Setter
public class Employee {
    private Double salary;

    public boolean isValid() {
        return name != null && !name.isEmpty() && age != null && age > 0 && salary != null && salary > 50000000;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;

    public Employee() {
    }

    public Employee(Long id, String name, Integer age, Double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
}

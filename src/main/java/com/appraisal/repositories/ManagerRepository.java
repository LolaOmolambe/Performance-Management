package com.appraisal.repositories;

import com.appraisal.entities.Employee;
import com.appraisal.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    boolean existsByEmployee(Employee employee);
}

package com.appraisal.repositories;

import com.appraisal.entities.EmployeeManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeManagerRepository extends JpaRepository<EmployeeManager, Long> {
}

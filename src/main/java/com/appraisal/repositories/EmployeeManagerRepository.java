package com.appraisal.repositories;

import com.appraisal.entities.Employee;
import com.appraisal.entities.EmployeeManager;
import com.appraisal.entities.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeManagerRepository extends JpaRepository<EmployeeManager, Long> {
    boolean existsByEmployee(Employee employee);

   Page<EmployeeManager> findAllByManager(Manager manager, Pageable pageable);
}

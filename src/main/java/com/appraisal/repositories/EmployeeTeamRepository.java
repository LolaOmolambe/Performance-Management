package com.appraisal.repositories;

import com.appraisal.entities.EmployeeTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeTeamRepository extends JpaRepository<EmployeeTeam, Long> {
}

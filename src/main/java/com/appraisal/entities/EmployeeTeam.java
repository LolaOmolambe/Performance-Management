package com.appraisal.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employee_teams")
public class EmployeeTeam extends BaseEntity {
    @ManyToOne
    @JoinColumn(
            name = "team_id",
            referencedColumnName = "id"
    )
    private Team team;

    @ManyToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id"
    )
    private Employee employee;
}

package com.appraisal.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "employee_managers")
public class EmployeeManager extends BaseEntity {
    @ManyToOne
    @JoinColumn(
            name = "manager_id",
            referencedColumnName = "id"
    )
    private Manager manager;

    @OneToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id"
    )
    private Employee employee;
}

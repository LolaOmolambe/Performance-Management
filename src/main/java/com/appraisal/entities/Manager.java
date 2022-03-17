package com.appraisal.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "managers")
public class Manager extends BaseEntity{
    @OneToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id"
    )
    private Employee employee;
}

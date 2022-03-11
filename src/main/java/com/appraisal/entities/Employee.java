package com.appraisal.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee extends BaseEntity {
    private String firstName;

    private String lastName;

    private Timestamp dateEmployed;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
}

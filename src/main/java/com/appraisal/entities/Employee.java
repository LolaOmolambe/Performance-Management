package com.appraisal.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employees")
public class Employee extends BaseEntity {
    private String firstName;

    private String lastName;

    private LocalDateTime dateEmployed;

    private String emailAddress;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
}

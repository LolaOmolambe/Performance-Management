package com.appraisal.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    private String name;
}

package com.appraisal.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question extends BaseEntity {
    private String questionText;
}

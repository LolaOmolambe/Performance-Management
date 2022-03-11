package com.appraisal.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "feedback_request_entry")
public class FeedbackRequestEntry extends BaseEntity {
    @ManyToOne()
    @JoinColumn(
            name = "feedback_request_id",
            referencedColumnName = "id"
    )
    private FeedbackRequest feedbackRequest;

    @ManyToOne()
    @JoinColumn(
            name = "question_id",
            referencedColumnName = "id"
    )
    private Question question;

    private String answer;

}

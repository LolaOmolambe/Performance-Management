package com.appraisal.entities;

import com.appraisal.enums.RequestStatus;
import com.appraisal.enums.RequestType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "feedback_requests")
public class FeedbackRequest extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @ManyToOne
    @JoinColumn(
            name = "requester_id",
            referencedColumnName = "id"
    )
    private User requester;

    @ManyToOne
    @JoinColumn(
            name = "actor_id",
            referencedColumnName = "id"
    )
    private Employee actor;

    @ManyToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "id"
    )
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    private LocalDateTime expiry;
}

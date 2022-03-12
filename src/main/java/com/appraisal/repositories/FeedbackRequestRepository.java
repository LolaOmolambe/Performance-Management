package com.appraisal.repositories;

import com.appraisal.entities.FeedbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRequestRepository extends JpaRepository<FeedbackRequest, Long> {
}

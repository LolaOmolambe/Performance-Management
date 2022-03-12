package com.appraisal.repositories;

import com.appraisal.entities.FeedbackRequestEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRequestEntryRepository extends JpaRepository<FeedbackRequestEntry, Long> {
}

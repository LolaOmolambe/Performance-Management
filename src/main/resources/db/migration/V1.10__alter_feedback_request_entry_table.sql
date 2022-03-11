ALTER TABLE feedback_request_entry
    ADD CONSTRAINT UNQ_request_question UNIQUE (feedback_request_id, question_id);
CREATE TABLE exam_results (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    exam_id BIGINT,
    score INT NOT NULL,
    passed_date_time TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

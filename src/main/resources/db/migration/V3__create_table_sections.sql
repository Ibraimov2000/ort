CREATE TABLE sections (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    questions_count INT NOT NULL,
    exam_id BIGINT,
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (exam_id) REFERENCES exams(id)
);

CREATE TABLE questions (
    id BIGSERIAL PRIMARY KEY,
    text TEXT NOT NULL,
    section_id BIGINT,
    score DOUBLE PRECISION NOT NULL,
    question_type VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (section_id) REFERENCES sections(id)
);

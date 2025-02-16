CREATE TABLE user_answers (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    question_id BIGINT,
    answer_id BIGINT,
    is_correct BOOLEAN NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (question_id) REFERENCES questions(id),
    FOREIGN KEY (answer_id) REFERENCES answers(id)
);

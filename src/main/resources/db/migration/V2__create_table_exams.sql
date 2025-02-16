CREATE TABLE exams (
   id BIGSERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   exam_type VARCHAR(255) NOT NULL,
   description TEXT,
   exam_language VARCHAR(255) NOT NULL,
   duration INT NOT NULL,  -- Duration in seconds
   start_time TIMESTAMP,
   end_time TIMESTAMP,
   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

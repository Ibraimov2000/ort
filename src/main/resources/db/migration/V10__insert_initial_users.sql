INSERT INTO users (first_name, last_name, email, password, role, created_at)
VALUES
    ('Admin', 'Adminov', 'admin@example.com', '{bcrypt_password_for_admin}', 'ADMIN', CURRENT_TIMESTAMP),
    ('Test', 'Testov', 'test@example.com', '{bcrypt_password_for_test_user}', 'USER', CURRENT_TIMESTAMP);

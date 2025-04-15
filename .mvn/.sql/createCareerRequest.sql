CREATE TABLE IF NOT EXISTS career_request(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    body TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    is_checked BOOLEAN
);
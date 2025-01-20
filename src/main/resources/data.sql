INSERT INTO users (user_id, login_id, password, nickname, birth_date, gender, location, role, is_admin, deleted, created_at, updated_at)
VALUES
    (UUID(), 'admin', '$2a$10$C1MoHm1jGNQ483aGGzHrZe6Cmzri4QJ/rPTF9UXug4XkKbk5fKEGm', '관리자', '1990-01-01', 'MALE', 'KOR', 'ROLE_ADMIN', TRUE, FALSE, NOW(), NOW()),
    (UUID(), 'testuser', '$2a$10$gCtPVGRsOwW/7iydT6z5n.6XYZHQzl0tQbEwEHHG3IfBNCqZBMnR2', '테스트유저', '1995-06-15', 'FEMALE', 'USA', 'ROLE_USER', FALSE, FALSE, NOW(), NOW());

-- 샘플 OAuth 인증 정보 추가
INSERT INTO auth (user_id, auth_type, access_token, refresh_token, created_at, updated_at)
VALUES
    ((SELECT user_id FROM users WHERE login_id = 'admin'), 'LOCAL', 'access_token_1', 'refresh_token_1', NOW(), NOW()),
    ((SELECT user_id FROM users WHERE login_id = 'testuser'), 'LOCAL', 'access_token_2', 'refresh_token_2', NOW(), NOW());
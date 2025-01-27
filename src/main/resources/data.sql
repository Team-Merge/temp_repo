INSERT INTO users (user_id, login_id, password, nickname, birth_date, gender, location, role, is_admin, deleted, created_at, updated_at)
VALUES
    (UUID(), 'admin', '$2a$10$9Z4rebsdT3XBHwdyzLih1eXe5ev5mvf0b8cm06oMo4IbiiijRIIty', '관리자', '1990-01-01', 'MALE', 'KOR', 'ROLE_ADMIN', TRUE, FALSE, NOW(), NOW()),
    (UUID(), 'testuser', '$2a$10$/HRHRfMRkPE4iracvWK.Zu9mVSK/BQd877HnqxxPlZ.RMnyKH5vOe', '테스트유저', '1995-06-15', 'FEMALE', 'USA', 'ROLE_USER', FALSE, TRUE, NOW(), NOW())
        ,
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'dayoon', '$2a$10$es28X.KDMFwPLHY/f1VGo.nB8ikfIxTH6VW8W9VvKrP8.hkrXEqwu', '다윤', '1995-06-15', 'FEMALE', 'USA', 'ROLE_USER', FALSE, FALSE, NOW(), NOW());

-- 샘플 OAuth 인증 정보 추가
INSERT INTO auth (user_id, auth_type, access_token, refresh_token, created_at, updated_at)
VALUES
    ((SELECT user_id FROM users WHERE login_id = 'admin'), 'LOCAL', 'access_token_1', 'refresh_token_1', NOW(), NOW()),
    ((SELECT user_id FROM users WHERE login_id = 'testuser'), 'LOCAL', 'access_token_2', 'refresh_token_2', NOW(), NOW());


--대화 기록
INSERT INTO conversation_history (user_id, conversation_question, conversation_answer, conversation_latitude, conversation_longitude, conversation_datetime)
VALUES
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'What is the weather today?', 'The weather is sunny with a chance of rain in the afternoon.', 37.5665, 126.978, '2023-09-25T14:30:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'How do I get to the nearest subway station?', 'Walk straight for 200 meters, then take a left turn. The subway station will be on your right.', 37.5665, 126.978, '2023-09-25T15:00:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'What are the opening hours of the nearest restaurant?', 'The restaurant opens at 11 AM and closes at 10 PM.', 37.5665, 126.978, '2023-09-25T16:00:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'Can you recommend a good hotel?', 'I recommend Hotel ABC which is only 10 minutes away by foot.', 37.5665, 126.978, '2023-09-25T16:30:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'How far is the museum from here?', 'It is 2 kilometers away, approximately a 15-minute walk.', 37.5670, 126.978, '2023-09-25T17:00:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'Is there a pharmacy nearby?', 'Yes, there is a pharmacy on the corner of this street.', 37.5665, 126.979, '2023-09-25T17:30:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'What time does the train leave?', 'The next train to Busan departs at 7:30 PM.', 37.5665, 126.978, '2023-09-25T18:00:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'What is the best place to visit in Seoul?', 'The best places include Gyeongbokgung Palace, Bukchon Hanok Village, and N Seoul Tower.', 37.5665, 126.978, '2023-09-25T18:30:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'Where can I find good shopping districts?', 'Myeongdong and Insadong are popular shopping areas.', 37.5665, 126.979, '2023-09-25T19:00:00'),
    ('502eaceb-bec1-43db-addc-e33bb430e29d', 'Is there a public park nearby?', 'Yes, there is a park about 10 minutes walk from here.', 37.5665, 126.980, '2023-09-25T19:30:00');

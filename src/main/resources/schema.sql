--SET REFERENTIAL_INTEGRITY FALSE;
--DELETE FROM auth;
--DELETE FROM users;
--SET REFERENTIAL_INTEGRITY TRUE;

--`user` 테이블 생성
CREATE TABLE IF NOT EXISTS users (
    user_id UUID PRIMARY KEY,
    login_id VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nickname VARCHAR(30) NOT NULL,
    birth_date DATE NOT NULL,
    gender ENUM('MALE', 'FEMALE') NOT NULL,
    location ENUM('KOR', 'USA', 'JPN') NOT NULL,
    role ENUM('ROLE_USER', 'ROLE_ADMIN') NOT NULL DEFAULT 'ROLE_USER',
    is_admin BOOLEAN NOT NULL DEFAULT FALSE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- `auth` 테이블 생성 (소셜 로그인 & 토큰 정보 저장)
CREATE TABLE IF NOT EXISTS auth (
    auth_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id UUID NOT NULL,
    auth_type ENUM('LOCAL', 'NAVER', 'GOOGLE', 'KAKAO') NOT NULL,
    access_token VARCHAR(500) NOT NULL,
    refresh_token VARCHAR(500) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

---- `place` 테이블 생성
--CREATE TABLE IF NOT EXISTS place (
--    place_id BIGINT PRIMARY KEY AUTO_INCREMENT,
--    type ENUM('맛집', '오락_체험', '힐링', '역사_문화', '쇼핑') NOT NULL,
--    name VARCHAR(255) NOT NULL,
--    tel VARCHAR(20),
--    latitude DOUBLE NOT NULL,
--    longitude DOUBLE NOT NULL,
--    address VARCHAR(50) NOT NULL,
--    deleted BOOLEAN NOT NULL DEFAULT FALSE,
--    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
--    updated_at DATETIME NULL ON UPDATE CURRENT_TIMESTAMP
--);


--대화 기록
CREATE TABLE IF NOT EXISTS conversation_history (
   conversation_id IDENTITY PRIMARY KEY,
   user_id UUID NOT NULL,
    conversation_question VARCHAR(1000),
    conversation_answer VARCHAR(1000),
    conversation_latitude DOUBLE NOT NULL,
    conversation_longitude DOUBLE NOT NULL,
    conversation_datetime TIMESTAMP NOT NULL
    );
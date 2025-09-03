-- 데이터베이스 생성
CREATE DATABASE IF NOT EXISTS book_search CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 사용
USE book_search;

-- Book 테이블 생성
CREATE TABLE IF NOT EXISTS book (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(255) NOT NULL UNIQUE,
    title VARCHAR(255),
    subtitle VARCHAR(255),
    book_cover_image_url VARCHAR(255),
    author VARCHAR(255),
    publisher VARCHAR(255),
    published INT,
    rating INT,
    description TEXT,
    price DECIMAL(10,2),
    FULLTEXT(title, subtitle)
);

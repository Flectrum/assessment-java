DROP DATABASE IF EXISTS quiz_application;

CREATE DATABASE quiz_application;

USE quiz_application;

CREATE TABLE question (question_id INT PRIMARY KEY AUTO_INCREMENT, difficulty_rank INT,
                       topic VARCHAR(30), content VARCHAR(300));

CREATE TABLE response (response_id INT PRIMARY KEY AUTO_INCREMENT, question_id INT NOT NULL,
                       correct BOOLEAN, text VARCHAR(200), FOREIGN KEY (question_id) REFERENCES question (question_id) ON DELETE CASCADE);

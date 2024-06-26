---- jdbc/schema.sql
--
---- Create the users table
--CREATE TABLE IF NOT EXISTS users (
--    id INT PRIMARY KEY AUTO_INCREMENT,
--    username VARCHAR(50) NOT NULL,
--    password VARCHAR(100) NOT NULL,
--    enabled BOOLEAN NOT NULL
--);
--
---- Create the authorities table
--CREATE TABLE IF NOT EXISTS authorities (
--    id INT PRIMARY KEY AUTO_INCREMENT,
--    username VARCHAR(50) NOT NULL,
--    authority VARCHAR(50) NOT NULL,
--    FOREIGN KEY (username) REFERENCES users (username)
--);
--
---- Insert sample user data with plain text passwords
--INSERT INTO users (username, password, enabled) VALUES
--('user1', 'password1', true),
--('user2', 'password2', true);
--
---- Insert sample authorities (roles)
--INSERT INTO authorities (username, authority) VALUES
--('user1', 'ROLE_USER'),
--('user2', 'ROLE_USER');

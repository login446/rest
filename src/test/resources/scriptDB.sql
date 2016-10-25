DROP TABLE IF EXISTS users;
CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45) NOT NULL,
  score INT NOT NULL,
  PRIMARY KEY (id)
);
INSERT INTO users(name, score) VALUES('ConflictName', 0);
INSERT INTO users(name, score) VALUES('DeleteName', 0);
INSERT INTO users(name, score) VALUES('Rename', 0);
INSERT INTO users(name, score) VALUES('RenameOk', 0);
INSERT INTO users(name, score) VALUES('ReScore', 0);
INSERT INTO users (username, password, enable, name, lastname, email) VALUES ('robertito22', '$2a$10$fNBAIcvLTeE/yfSV8akSqe7gpgLfkW7HhdTcdn0khChSMTtOeIH3C', true, 'Fabricio', 'Echasny', 'campeonRebenque04@gmail.com');
INSERT INTO users (username, password, enable, name, lastname, email) VALUES ('homx12', '$2a$10$OTo0HuJBNQabGKkaao0tfOSPlZOK/4xCkoqm6yjQM3p77IwMJzhEm', true, 'Venicio', 'Podolotoli', 'ktraska4ever@gmail.com');

INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');

INSERT INTO users_to_roles(user_id, role_id) VALUES (1, 1);
INSERT INTO users_to_roles(user_id, role_id) VALUES (2, 2);
INSERT INTO users_to_roles(user_id, role_id) VALUES (2, 1);
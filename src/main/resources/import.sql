INSERT INTO roles(name)
VALUES ('ROLE_USER');
INSERT INTO roles(name)
VALUES ('ROLE_MODERATOR');
INSERT INTO roles(name)
VALUES ('ROLE_ADMIN');
INSERT INTO users(email, password, username)
VALUES ('admin@test.com', '$2a$10$35Wq7vRk7Zi1v.rbV4CaMeTLal8RgSBNlNSAPGzgGCyFki2CQvamW', 'admin');
INSERT INTO users(email, password, username)
VALUES ('mod@test.com', '$2a$10$qkOl2p2sP1zWpuw0Juyp5.xJqWX0A/s9Z4i5FxVHQecR8neEArYKm', 'moderator');
INSERT INTO user_roles(user_id, role_id)
VALUES (1, 3);
INSERT INTO user_roles(user_id, role_id)
VALUES (2, 2);
INSERT INTO user_roles(user_id, role_id)
VALUES (2, 1);
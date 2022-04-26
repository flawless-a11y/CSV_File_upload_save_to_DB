INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');
INSERT INTO users(email,name, password, username) VALUES ('admin@test.com','Administrator', '$2a$10$35Wq7vRk7Zi1v.rbV4CaMeTLal8RgSBNlNSAPGzgGCyFki2CQvamW', 'admin');
INSERT INTO users(email,name, password, username) VALUES ('mod@test.com','Moderator', '$2a$10$qkOl2p2sP1zWpuw0Juyp5.xJqWX0A/s9Z4i5FxVHQecR8neEArYKm', 'moderator');
INSERT INTO users(email,name, password, username) VALUES ('merchant@test.com','Merchant_Catalogue', '$2a$10$35Wq7vRk7Zi1v.rbV4CaMeTLal8RgSBNlNSAPGzgGCyFki2CQvamW','prashant');
INSERT INTO users(email,name, password, username) VALUES ('product@test.com','Product_Catalogue', '$2a$10$35Wq7vRk7Zi1v.rbV4CaMeTLal8RgSBNlNSAPGzgGCyFki2CQvamW', 'anirudh');
INSERT INTO users(email,name, password, username) VALUES ('order@test.com','Order_Management', '$2a$10$35Wq7vRk7Zi1v.rbV4CaMeTLal8RgSBNlNSAPGzgGCyFki2CQvamW','mayur1');
INSERT INTO users(email,name, password, username) VALUES ('payout@test.com','Merchant_Payout', '$2a$10$35Wq7vRk7Zi1v.rbV4CaMeTLal8RgSBNlNSAPGzgGCyFki2CQvamW', 'charan');
INSERT INTO users(email,name, password, username) VALUES ('user@test.com','User_Management','$2a$10$35Wq7vRk7Zi1v.rbV4CaMeTLal8RgSBNlNSAPGzgGCyFki2CQvamW','mayur');
INSERT INTO user_roles(user_id, role_id) VALUES (1, 3);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles(user_id, role_id) VALUES (2, 1);
INSERT INTO user_roles(user_id, role_id) VALUES (3, 3);
INSERT INTO user_roles(user_id, role_id) VALUES (4, 3);
INSERT INTO user_roles(user_id, role_id) VALUES (5, 3);
INSERT INTO user_roles(user_id, role_id) VALUES (6, 3);
INSERT INTO user_roles(user_id, role_id) VALUES (7, 3);



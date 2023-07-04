GRANT ALL ON ALL TABLES IN SCHEMA public TO siatka_admin;
REVOKE ALL ON DATABASE postgres FROM siatka_admin;

REVOKE ALL ON ALL TABLES IN SCHEMA public FROM siatka_mok;
GRANT ALL ON TABLE admins, captains, coaches, managers, referees, roles, accounts TO siatka_mok;
REVOKE ALL ON DATABASE postgres FROM siatka_mok;

REVOKE ALL ON ALL TABLES IN SCHEMA public FROM siatka_mzl;
REVOKE ALL ON DATABASE postgres FROM siatka_mzl;
GRANT ALL ON ALL TABLES IN SCHEMA public TO siatka_mzl;


REVOKE ALL ON ALL TABLES IN SCHEMA public FROM siatka_auth;
GRANT ALL ON TABLE roles, accounts TO siatka_auth;
REVOKE ALL ON DATABASE postgres FROM siatka_auth;

BEGIN;

-- Wstawianie danych do tabeli leagues
INSERT INTO leagues (id, version, league_number, season) VALUES ('123e4567-e89b-12d3-a456-426655440001', 1, 1, '2022/2023');
INSERT INTO leagues (id, version, league_number, season) VALUES ('223e4567-e89b-12d3-a456-426655440001', 1, 2, '2022/2023');
INSERT INTO leagues (id, version, league_number, season) VALUES ('323e4567-e89b-12d3-a456-426655440001', 1, 3, '2022/2023');
INSERT INTO leagues (id, version, league_number, season) VALUES ('423e4567-e89b-12d3-a456-426655440001', 1, 4, '2022/2023');

-- Wstawianie danych do tabeli accounts
-- pass: very_strong_admin_password
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('d93b7ab9-da9b-490c-a096-5c48437cd3a9', 0, 'admin@als.pl', 'true', 'true', 'false', 'admin', 'en', 'admin', '2023-05-18 18:53:32.000000', 'admin', '$2a$12$Y4dyfYOWuEm3L6UMCmyFuuu.Z.aJydrRNLYFu8u/qBwtNRQ2iehE2');
-- pass: very_strong_admin_account1_password
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('123e4567-e89b-12d3-a456-426655440007', 1, 'account1@example.com', 'false', 'false', 'true', 'Doe', 'en_US', 'account1', CURRENT_TIMESTAMP, 'John', '$2a$12$AqvUA/JJ6df/.SIXJjlAjeIKpLwipeVc7fWDbPld3VcOXTr294GVG');
-- pass: very_strong_captain_account2_password
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('223e4567-e89b-12d3-a456-426655440007', 1, 'account2@example.com', 'true', 'true', 'false', 'Smith', 'en_GB', 'account2', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
-- pass: very_strong_coach_account3_password
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('323e4567-e89b-12d3-a456-426655440007', 1, 'account3@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'account3', CURRENT_TIMESTAMP, 'Mike', '$2a$12$1CtPITSn8pyx7AxDqjYpnuPycbraJGLaaXViqwXXDaEBMraAucfsi');
-- pass: very_strong_referee_account4_password
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('423e4567-e89b-12d3-a456-426655440007', 1, 'account4@example.com', 'true', 'true', 'false', 'Smith', 'en_GB', 'account4', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Y5UDWXD4hHG8Cxlq4tSmweBwH4wLqVTfZU/0ceRGF6Ik6WWy2KskO');
-- pass: very_strong_manager_account5_password
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('523e4567-e89b-12d3-a456-426655440007', 1, 'account5@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'account5', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
-- Wstawianie danych do tabeli roles
INSERT INTO roles (id, version, role_type, account_id) VALUES ('123e4567-e89b-12d3-a456-426655440008', 1, 'ADMIN', '123e4567-e89b-12d3-a456-426655440007');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('223e4567-e89b-12d3-a456-426655441008', 1, 'CAPTAIN', '223e4567-e89b-12d3-a456-426655440007');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('323e4567-e89b-12d3-a456-426655441008', 1, 'COACH', '323e4567-e89b-12d3-a456-426655440007');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('423e4567-e89b-12d3-a456-426655440008', 1, 'REFEREE', '423e4567-e89b-12d3-a456-426655440007');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('523e4567-e89b-12d3-a456-426655440008', 1, 'MANAGER', '523e4567-e89b-12d3-a456-426655440007');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('5570ecea-3f89-41e3-9574-f4890b2e593f', 0, 'ADMIN', 'd93b7ab9-da9b-490c-a096-5c48437cd3a9');

-- Wstawianie danych do tabeli admins
INSERT INTO admins (id) VALUES ('123e4567-e89b-12d3-a456-426655440008');
INSERT INTO admins (id) VALUES ('5570ecea-3f89-41e3-9574-f4890b2e593f');
-- Wstawianie danych do tabeli captains
INSERT INTO captains (id) VALUES ('223e4567-e89b-12d3-a456-426655441008');

-- Wstawianie danych do tabeli coaches
INSERT INTO coaches (id) VALUES ('323e4567-e89b-12d3-a456-426655441008');

-- Wstawianie danych do tabeli referees
INSERT INTO referees (id) VALUES ('423e4567-e89b-12d3-a456-426655440008');

INSERT INTO managers (id) VALUES ('523e4567-e89b-12d3-a456-426655440008');

-- team 01
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-01bd-11ee-bbf9-023dc71ffe3b', 1, 'coach1@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach1', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-01bd-11ee-9927-023dc71ffe3b', 1, 'captain1@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain1', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-01be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-01bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-01be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-01bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-01be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-01be-11ee-8d2b-023dc71ffe3b');

-- team 02
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-02bd-11ee-bbf9-023dc71ffe3b', 1, 'coach2@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach2', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-02bd-11ee-9927-023dc71ffe3b', 1, 'captain2@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain2', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-02be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-02bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-02be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-02bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-02be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-02be-11ee-8d2b-023dc71ffe3b');

-- team 03
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-03bd-11ee-bbf9-023dc71ffe3b', 1, 'coach3@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach3', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-03bd-11ee-9927-023dc71ffe3b', 1, 'captain3@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain3', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-03be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-03bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-03be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-03bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-03be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-03be-11ee-8d2b-023dc71ffe3b');

-- team 04
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-04bd-11ee-bbf9-023dc71ffe3b', 1, 'coach4@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach4', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-04bd-11ee-9927-023dc71ffe3b', 1, 'captain4@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain4', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-04be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-04bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-04be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-04bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-04be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-04be-11ee-8d2b-023dc71ffe3b');

-- team 05
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-05bd-11ee-bbf9-023dc71ffe3b', 1, 'coach5@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach5', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-05bd-11ee-9927-023dc71ffe3b', 1, 'captain5@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain5', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-05be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-05bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-05be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-05bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-05be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-05be-11ee-8d2b-023dc71ffe3b');

-- team 06
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-06bd-11ee-bbf9-023dc71ffe3b', 1, 'coach6@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach6', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-06bd-11ee-9927-023dc71ffe3b', 1, 'captain6@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain6', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-06be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-06bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-06be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-06bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-06be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-06be-11ee-8d2b-023dc71ffe3b');

-- team 07
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-07bd-11ee-bbf9-023dc71ffe3b', 1, 'coach7@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach7', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-07bd-11ee-9927-023dc71ffe3b', 1, 'captain7@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain7', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-07be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-07bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-07be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-07bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-07be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-07be-11ee-8d2b-023dc71ffe3b');

-- team 08
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-08bd-11ee-bbf9-023dc71ffe3b', 1, 'coach8@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach8', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-08bd-11ee-9927-023dc71ffe3b', 1, 'captain8@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain8', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-08be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-08bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-08be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-08bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-08be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-08be-11ee-8d2b-023dc71ffe3b');

-- team 09
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-09bd-11ee-bbf9-023dc71ffe3b', 1, 'coach9@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach9', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-09bd-11ee-9927-023dc71ffe3b', 1, 'captain9@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain9', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-09be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-09bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-09be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-09bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-09be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-09be-11ee-8d2b-023dc71ffe3b');

-- team 10
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-10bd-11ee-bbf9-023dc71ffe3b', 1, 'coach10@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach10', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-10bd-11ee-9927-023dc71ffe3b', 1, 'captain10@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain10', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-10be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-10bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-10be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-10bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-10be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-10be-11ee-8d2b-023dc71ffe3b');

-- team 11
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-11bd-11ee-bbf9-023dc71ffe3b', 1, 'coach11@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach11', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-11bd-11ee-9927-023dc71ffe3b', 1, 'captain11@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain11', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-11be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-11bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-11be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-11bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-11be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-11be-11ee-8d2b-023dc71ffe3b');

-- team 12
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-12bd-11ee-bbf9-023dc71ffe3b', 1, 'coach12@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach12', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-12bd-11ee-9927-023dc71ffe3b', 1, 'captain12@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain12', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-12be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-12bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-12be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-12bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-12be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-12be-11ee-8d2b-023dc71ffe3b');

-- team 13
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-13bd-11ee-bbf9-023dc71ffe3b', 1, 'coach13@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach13', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-13bd-11ee-9927-023dc71ffe3b', 1, 'captain13@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain13', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-13be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-13bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-13be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-13bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-13be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-13be-11ee-8d2b-023dc71ffe3b');

-- team 14
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-14bd-11ee-bbf9-023dc71ffe3b', 1, 'coach14@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach14', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-14bd-11ee-9927-023dc71ffe3b', 1, 'captain14@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain14', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-14be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-14bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-14be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-14bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-14be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-14be-11ee-8d2b-023dc71ffe3b');

-- team 15
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-15bd-11ee-bbf9-023dc71ffe3b', 1, 'coach15@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach15', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-15bd-11ee-9927-023dc71ffe3b', 1, 'captain15@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain15', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-15be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-15bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-15be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-15bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-15be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-15be-11ee-8d2b-023dc71ffe3b');

-- team 16
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-16bd-11ee-bbf9-023dc71ffe3b', 1, 'coach16@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach16', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-16bd-11ee-9927-023dc71ffe3b', 1, 'captain16@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain16', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-16be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-16bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-16be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-16bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-16be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-16be-11ee-8d2b-023dc71ffe3b');

-- team 17
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-17bd-11ee-bbf9-023dc71ffe3b', 1, 'coach17@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach17', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-17bd-11ee-9927-023dc71ffe3b', 1, 'captain17@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain17', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-17be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-17bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-17be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-17bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-17be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-17be-11ee-8d2b-023dc71ffe3b');

-- team 18
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-18bd-11ee-bbf9-023dc71ffe3b', 1, 'coach18@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach18', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-18bd-11ee-9927-023dc71ffe3b', 1, 'captain18@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain18', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-18be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-18bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-18be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-18bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-18be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-18be-11ee-8d2b-023dc71ffe3b');

-- team 19
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-19bd-11ee-bbf9-023dc71ffe3b', 1, 'coach19@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach19', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-19bd-11ee-9927-023dc71ffe3b', 1, 'captain19@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain19', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-19be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-19bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-19be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-19bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-19be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-19be-11ee-8d2b-023dc71ffe3b');

-- team 20
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-20bd-11ee-bbf9-023dc71ffe3b', 1, 'coach20@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach20', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-20bd-11ee-9927-023dc71ffe3b', 1, 'captain20@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain20', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-20be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-20bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-20be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-20bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-20be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-20be-11ee-8d2b-023dc71ffe3b');

-- team 21
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-21bd-11ee-bbf9-023dc71ffe3b', 1, 'coach21@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach21', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-21bd-11ee-9927-023dc71ffe3b', 1, 'captain21@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain21', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-21be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-21bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-21be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-21bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-21be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-21be-11ee-8d2b-023dc71ffe3b');

-- team 22
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-22bd-11ee-bbf9-023dc71ffe3b', 1, 'coach22@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach22', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-22bd-11ee-9927-023dc71ffe3b', 1, 'captain22@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain22', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-22be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-22bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-22be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-22bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-22be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-22be-11ee-8d2b-023dc71ffe3b');

-- team 23
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-23bd-11ee-bbf9-023dc71ffe3b', 1, 'coach23@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach23', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-23bd-11ee-9927-023dc71ffe3b', 1, 'captain23@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain23', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-23be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-23bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-23be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-23bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-23be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-23be-11ee-8d2b-023dc71ffe3b');

-- team 24
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-24bd-11ee-bbf9-023dc71ffe3b', 1, 'coach24@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach24', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-24bd-11ee-9927-023dc71ffe3b', 1, 'captain24@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain24', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-24be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-24bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-24be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-24bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-24be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-24be-11ee-8d2b-023dc71ffe3b');

-- team 25
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-25bd-11ee-bbf9-023dc71ffe3b', 1, 'coach25@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach25', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-25bd-11ee-9927-023dc71ffe3b', 1, 'captain25@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain25', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-25be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-25bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-25be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-25bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-25be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-25be-11ee-8d2b-023dc71ffe3b');

-- team 26
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-26bd-11ee-bbf9-023dc71ffe3b', 1, 'coach26@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach26', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-26bd-11ee-9927-023dc71ffe3b', 1, 'captain26@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain26', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-26be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-26bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-26be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-26bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-26be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-26be-11ee-8d2b-023dc71ffe3b');

-- team 27
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-27bd-11ee-bbf9-023dc71ffe3b', 1, 'coach27@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach27', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-27bd-11ee-9927-023dc71ffe3b', 1, 'captain27@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain27', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-27be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-27bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-27be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-27bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-27be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-27be-11ee-8d2b-023dc71ffe3b');

-- team 28
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-28bd-11ee-bbf9-023dc71ffe3b', 1, 'coach28@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach28', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-28bd-11ee-9927-023dc71ffe3b', 1, 'captain28@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain28', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-28be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-28bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-28be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-28bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-28be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-28be-11ee-8d2b-023dc71ffe3b');

-- team 29
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-29bd-11ee-bbf9-023dc71ffe3b', 1, 'coach29@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach29', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-29bd-11ee-9927-023dc71ffe3b', 1, 'captain29@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain29', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-29be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-29bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-29be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-29bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-29be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-29be-11ee-8d2b-023dc71ffe3b');

-- team 30
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-30bd-11ee-bbf9-023dc71ffe3b', 1, 'coach30@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach30', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-30bd-11ee-9927-023dc71ffe3b', 1, 'captain30@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain30', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-30be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-30bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-30be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-30bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-30be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-30be-11ee-8d2b-023dc71ffe3b');

-- team 31
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-31bd-11ee-bbf9-023dc71ffe3b', 1, 'coach31@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach31', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-31bd-11ee-9927-023dc71ffe3b', 1, 'captain31@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain31', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-31be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-31bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-31be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-31bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-31be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-31be-11ee-8d2b-023dc71ffe3b');

-- team 32
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9707fe86-32bd-11ee-bbf9-023dc71ffe3b', 1, 'coach32@example.com', 'true', 'true', 'false', 'Johnson', 'en_US', 'coach32', CURRENT_TIMESTAMP, 'Mike', '$2a$12$2Ruwi0uhzk3HZduwhSgQVuzesAY6ONsPasEzdp5zHfTGx.O7TPs.S');
INSERT INTO accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('9c987baa-32bd-11ee-9927-023dc71ffe3b', 1, 'captain32@example.com', 'true', 'true', 'false', 'Brownse', 'en_GB', 'captain32', CURRENT_TIMESTAMP, 'Jane', '$2a$12$Dv.ZXRWl10bn6UdO7LUtJ.RXBnrBy/JyNjXxQWTmsF0AbYI.WFonS');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('227f870e-32be-11ee-9619-023dc71ffe3b', 1, 'COACH', '9707fe86-32bd-11ee-bbf9-023dc71ffe3b');
INSERT INTO roles (id, version, role_type, account_id) VALUES ('22e5391e-32be-11ee-8d2b-023dc71ffe3b', 1, 'CAPTAIN', '9c987baa-32bd-11ee-9927-023dc71ffe3b');
INSERT INTO coaches (id) VALUES ('227f870e-32be-11ee-9619-023dc71ffe3b');
INSERT INTO captains (id) VALUES ('22e5391e-32be-11ee-8d2b-023dc71ffe3b');

-- Wstawianie danych do tabeli teams
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('013e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Aspect', 1, '22e5391e-32be-11ee-8d2b-023dc71ffe3b', '227f870e-32be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('023e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Prize of Conflict', 1, '22e5391e-02be-11ee-8d2b-023dc71ffe3b', '227f870e-02be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('033e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Sundry', 1, '22e5391e-03be-11ee-8d2b-023dc71ffe3b', '227f870e-03be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('043e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Revolution of Carnage', 1, '22e5391e-04be-11ee-8d2b-023dc71ffe3b', '227f870e-04be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('053e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Virtue of Karma', 1, '22e5391e-05be-11ee-8d2b-023dc71ffe3b', '227f870e-05be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('063e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Ireland Lizards', 1, '22e5391e-06be-11ee-8d2b-023dc71ffe3b', '227f870e-06be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('073e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Ireland Mice', 1, '22e5391e-07be-11ee-8d2b-023dc71ffe3b', '227f870e-07be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('083e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Ireland Lions', 1, '22e5391e-08be-11ee-8d2b-023dc71ffe3b', '227f870e-08be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('093e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Ireland Twins', 1, '22e5391e-09be-11ee-8d2b-023dc71ffe3b', '227f870e-09be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('103e4567-e89b-12d3-a456-426655440010', 'Ireland', 'true', 'Team Ireland Browns', 1, '22e5391e-10be-11ee-8d2b-023dc71ffe3b', '227f870e-10be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('113e4567-e89b-12d3-a456-426655440010', 'Lodz', 'true', 'Team Lodz Foxes', 1, '22e5391e-11be-11ee-8d2b-023dc71ffe3b', '227f870e-11be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('123e4567-e89b-12d3-a456-426655440010', 'Lodz', 'true', 'Team Lodz Elephants', 1, '22e5391e-12be-11ee-8d2b-023dc71ffe3b', '227f870e-12be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('133e4567-e89b-12d3-a456-426655440010', 'Lodz', 'true', 'Team Lodz Kittens', 1, '22e5391e-13be-11ee-8d2b-023dc71ffe3b', '227f870e-13be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('143e4567-e89b-12d3-a456-426655440010', 'Lodz', 'true', 'Team Lodz Raiders', 1, '22e5391e-14be-11ee-8d2b-023dc71ffe3b', '227f870e-14be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('153e4567-e89b-12d3-a456-426655440010', 'Lodz', 'true', 'Team Lodz Greys', 1, '22e5391e-15be-11ee-8d2b-023dc71ffe3b', '227f870e-15be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('163e4567-e89b-12d3-a456-426655440010', 'Warsaw', 'true', 'Team Grey Foxes', 1, '22e5391e-16be-11ee-8d2b-023dc71ffe3b', '227f870e-16be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('173e4567-e89b-12d3-a456-426655440010', 'Warsaw', 'true', 'Team Funny Foxes', 1, '22e5391e-17be-11ee-8d2b-023dc71ffe3b', '227f870e-17be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('183e4567-e89b-12d3-a456-426655440010', 'Lodz', 'true', 'Team Funny Elephants', 1, '22e5391e-18be-11ee-8d2b-023dc71ffe3b', '227f870e-18be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('193e4567-e89b-12d3-a456-426655440010', 'Lodz', 'true', 'Team Funny Kittens', 1, '22e5391e-19be-11ee-8d2b-023dc71ffe3b', '227f870e-19be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('203e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Funny Raiders', 1, '22e5391e-20be-11ee-8d2b-023dc71ffe3b', '227f870e-20be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('213e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Sloppy Foxes', 1, '22e5391e-21be-11ee-8d2b-023dc71ffe3b', '227f870e-21be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('223e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Sloppy Elephants', 1, '22e5391e-22be-11ee-8d2b-023dc71ffe3b', '227f870e-22be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('233e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Sloppy Kittens', 1, '22e5391e-23be-11ee-8d2b-023dc71ffe3b', '227f870e-23be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('243e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Sloppy Raiders', 1, '22e5391e-24be-11ee-8d2b-023dc71ffe3b', '227f870e-24be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('253e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Splendid Foxes', 1, '22e5391e-25be-11ee-8d2b-023dc71ffe3b', '227f870e-25be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('263e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Splendid Elephants', 1, '22e5391e-26be-11ee-8d2b-023dc71ffe3b', '227f870e-26be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('273e4567-e89b-12d3-a456-426655440010', 'Poznan', 'true', 'Team Splendid Kittens', 1, '22e5391e-27be-11ee-8d2b-023dc71ffe3b', '227f870e-27be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('283e4567-e89b-12d3-a456-426655440010', 'London', 'true', 'Team Splendid Raiders', 1, '22e5391e-28be-11ee-8d2b-023dc71ffe3b', '227f870e-28be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('293e4567-e89b-12d3-a456-426655440010', 'London', 'true', 'Team London Badgers', 1, '22e5391e-29be-11ee-8d2b-023dc71ffe3b', '227f870e-29be-11ee-9619-023dc71ffe3b', '123e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('303e4567-e89b-12d3-a456-426655440010', 'London', 'true', 'Team London Ostriches', 1, '22e5391e-30be-11ee-8d2b-023dc71ffe3b', '227f870e-30be-11ee-9619-023dc71ffe3b', '223e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('313e4567-e89b-12d3-a456-426655440010', 'London', 'true', 'Team London Warriors', 1, '22e5391e-31be-11ee-8d2b-023dc71ffe3b', '227f870e-31be-11ee-9619-023dc71ffe3b', '323e4567-e89b-12d3-a456-426655440001');
INSERT INTO teams (id, city, is_approved, team_name, version, captain_id, coach_id, league_id) VALUES ('323e4567-e89b-12d3-a456-426655440010', 'London', 'true', 'Team London Creams', 1, '22e5391e-32be-11ee-8d2b-023dc71ffe3b', '227f870e-32be-11ee-9619-023dc71ffe3b', '423e4567-e89b-12d3-a456-426655440001');



-- Wstawianie danych do tabeli players
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bfbd63f6-5f12-45a0-bddb-d09df532ac7a', 35, 'Jennifer', 'true', 'Shelton', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5ac67b0d-6421-4fe9-8349-344a90183611', 37, 'Dylan', 'true', 'Carroll', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3ff79756-1820-4f52-b183-bce93c88ebfd', 19, 'Katrina', 'true', 'Mendoza', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ce3f4b60-5306-4aff-b842-72e7f64c39a9', 39, 'Stephanie', 'true', 'Garrison', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bc48c4e9-1fd3-432e-9406-cdb03d64f905', 39, 'Austin', 'true', 'Garcia', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('66c45716-ac6b-4499-8326-6a70f844d9b3', 31, 'Tom', 'true', 'Wright', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('41023bad-b322-4443-ae3f-d6b056ab8027', 21, 'Emily', 'true', 'Bernard', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4b679492-f66e-4a6c-a666-ec1ac9fc6d94', 32, 'Vicki', 'true', 'Powell', 1,  '013e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('45cf0ca1-b002-4bfd-b22b-d295f06351b5', 25, 'Michael', 'true', 'Little', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('164e17c4-fe55-4171-9b3e-906bf9a2e5fd', 37, 'Luke', 'true', 'Jimenez', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d51d1325-efb8-4879-8211-faa6bffcd341', 27, 'Rebecca', 'true', 'Rodriguez', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d0d2104c-95ab-421c-9ed4-e2e288730a0e', 19, 'Jason', 'true', 'Ware', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('03077706-1ab3-4ab3-8531-a1aae5c3a597', 28, 'Gabriel', 'true', 'Pratt', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b0f2a4ea-5bf3-408a-9567-38d32982b111', 18, 'Norman', 'true', 'Randolph', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c1fa8960-fb65-47e5-8354-06693c724fe4', 30, 'Natalie', 'true', 'Hamilton', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b47be575-75be-4a6d-a482-8d31105ea675', 23, 'Matthew', 'true', 'Moss', 1,  '023e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c4254db8-118e-4b2f-afca-7ec76fdd011d', 29, 'Kim', 'true', 'Banks', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('9fd5aa94-4aba-499b-aa0e-72603c3bc767', 27, 'Eddie', 'true', 'Davis', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3ea63984-3368-4f3a-b905-deed4ea8088b', 19, 'Daniel', 'true', 'Reynolds', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bb4806d8-9bfa-4c01-99e3-9aa660fcf1c9', 30, 'Jeremy', 'true', 'Becker', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('13b41868-8318-458b-9d99-2807180057d2', 35, 'Robert', 'true', 'Tucker', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a0e74761-fcf2-4f9a-b762-0a8a45e80ace', 39, 'Gary', 'true', 'Khan', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4202e1dd-b8af-4a90-81f7-35e923b93e3d', 20, 'Christy', 'true', 'Gibson', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c469d1e9-feb5-4663-a8db-c35723fdfba1', 28, 'Margaret', 'true', 'King', 1,  '033e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('07f8d39a-e47e-4747-be2c-78a0ef89b024', 38, 'Katrina', 'true', 'Gomez', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5021381e-a12a-4e5d-9e93-2fefbfc455db', 32, 'Tonya', 'true', 'Gonzalez', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('14a0b70f-c1b2-43b7-a228-533cfceec9c5', 20, 'Charles', 'true', 'Sparks', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('fe53609a-253c-43c6-88d5-aa237751d150', 36, 'Tammy', 'true', 'Martin', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('2901e293-27a3-4bc5-810f-0b8fb3c0b6ec', 20, 'Joyce', 'true', 'Hudson', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('505f8566-5888-40b1-92f2-10218723bea1', 32, 'Jerry', 'true', 'Wyatt', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('baf7a2c8-3d4a-4c30-aabc-27e43bbd378c', 32, 'Lauren', 'true', 'Bailey', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6beeb051-3448-45d0-8a8f-b49155d9d1d8', 35, 'Linda', 'true', 'Kelly', 1,  '043e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bd21a823-9026-413e-bc9d-51a8ecef6bfa', 39, 'Tyler', 'true', 'Smith', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d5153d32-769b-4fd8-8d51-0398bc1513ee', 31, 'Barbara', 'true', 'Jones', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('cc517862-cc8e-48a8-989a-50b6e6c8b185', 38, 'Alexis', 'true', 'Maxwell', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a80aea50-9e6a-4226-92a6-fe98ea4e67f4', 34, 'Charles', 'true', 'Robbins', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1fac3360-6160-4f6f-a28a-fb98c86751bb', 24, 'Dennis', 'true', 'Williams', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b878214b-9e7c-419d-a0b9-a4e333c50047', 37, 'Kathy', 'true', 'Singh', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3e7b6050-13d4-44e3-97cd-493865641f1d', 18, 'Daniel', 'true', 'Hanson', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('965fc507-6855-4581-8468-86f5a6e8326f', 30, 'Caitlin', 'true', 'Brooks', 1,  '053e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d4a1d230-3178-4cd8-a44a-05427531e264', 40, 'Jesse', 'true', 'Wright', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f78e0e3b-acef-4f7a-bb7c-284209abc126', 27, 'Heidi', 'true', 'Anderson', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e2ea0668-5891-48e0-9701-fd5788027409', 33, 'Beth', 'true', 'May', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3dcad865-d8e8-42f3-9164-60fa0eafa9f6', 18, 'Kimberly', 'true', 'Cole', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('710d7b23-0a6c-4b6b-bfcc-db6dbebb9fcb', 37, 'Henry', 'true', 'Mckenzie', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('acc6aa43-9540-47fd-8890-691011f4bbb2', 18, 'Brittany', 'true', 'Bishop', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('61e8b516-f57c-4f60-b9ad-82dce97c5193', 18, 'Haley', 'true', 'Avery', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('8f4ea04d-66dd-4483-88be-27709b77db19', 37, 'Sherri', 'true', 'Moore', 1,  '063e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6860a380-7355-4524-ae10-ad183ab18705', 28, 'Kristen', 'true', 'Humphrey', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('dd13fe7c-4604-41b6-af93-2e8039c81316', 24, 'Lee', 'true', 'Myers', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4d44bdc7-ae4f-485e-922e-e3c2102427f9', 21, 'Hannah', 'true', 'Taylor', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('578f2e33-06a7-4d11-ab6d-e53c77b2ecca', 37, 'David', 'true', 'Rivera', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4abf2c91-6306-4cdd-86b0-8c535556d59c', 28, 'Paul', 'true', 'Ortega', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ddb3826b-61aa-4308-a2fa-03c67a5ebeae', 38, 'Tonya', 'true', 'Byrd', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bacd0389-7a9a-4467-8905-bd618882c1f2', 25, 'John', 'true', 'May', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('53ebe714-4846-4177-b90e-c8e17dafbd10', 29, 'Robert', 'true', 'Rogers', 1,  '073e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5a50471c-4ad8-4f9f-a6ca-1564e2862e85', 26, 'Joshua', 'true', 'Baker', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c148d13f-5e22-44de-8467-5dc5c1492eaf', 30, 'Eric', 'true', 'Watkins', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bfab4713-3304-4a6e-b25d-c323093244a4', 34, 'Kelsey', 'true', 'Perez', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e31011bd-7bb6-4f9f-af97-f8aca8888633', 19, 'Matthew', 'true', 'Dennis', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4947717b-541b-4bfe-af77-7a369da190e5', 30, 'Jennifer', 'true', 'Kelly', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d94889cb-6d76-4226-8b5e-d8e2157f8df5', 21, 'Brian', 'true', 'Carter', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bc7f73c0-c96b-4051-96af-1b099e35db2e', 19, 'Gail', 'true', 'Diaz', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5db75371-04e8-417f-96a6-0803cda2ad55', 31, 'Kendra', 'true', 'Arroyo', 1,  '083e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d1473a02-ae55-4481-9358-86f273dedf40', 19, 'Susan', 'true', 'Anderson', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('09183c77-3983-4a0d-b1a1-d05a1cc95c81', 21, 'Brenda', 'true', 'Anderson', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ff63c579-4db9-4f44-a800-e57d6d912c19', 28, 'Marisa', 'true', 'Cooper', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a46c9e48-4014-4763-90ac-ae93697a3ecf', 23, 'Daniel', 'true', 'Myers', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a22c0c49-1437-4937-9ca0-f963e7e10871', 25, 'Ann', 'true', 'Young', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6e2c9fe0-1288-458c-b114-078700d87781', 32, 'Aaron', 'true', 'Guzman', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3a2b054f-e432-4894-83fa-ada1c7c6e224', 37, 'Kristin', 'true', 'Mendez', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('29c70534-560d-44d9-927e-e16eebb213fe', 36, 'Alyssa', 'true', 'Diaz', 1,  '093e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4f2a11eb-f19e-4b4c-8f9d-5b902f40274e', 18, 'Lori', 'true', 'Larsen', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f1880f0a-ed1e-460b-b06b-547382291360', 38, 'Rickey', 'true', 'Leonard', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c6111717-1251-43b1-814b-4638ffe2a3e6', 22, 'Jason', 'true', 'Johnson', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f57e7ef3-ace4-4bd7-a69d-5ec7ae6b4c19', 35, 'Danny', 'true', 'Snyder', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('80f63fa8-dd47-4577-a77c-5e7dfed3ee8b', 33, 'Brittney', 'true', 'Thomas', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('627be9da-557c-4053-af0f-f04f9cf8a4e8', 21, 'Albert', 'true', 'Schultz', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b30afc62-ed92-439a-b9de-a6081b092f43', 18, 'Carrie', 'true', 'Santos', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('45fceba9-7c19-4859-91a6-7aa47b6a00ff', 26, 'David', 'true', 'Williams', 1,  '103e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b2c89117-9ff3-4a54-a4ba-0c511a4ff44d', 31, 'Isaiah', 'true', 'Baldwin', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6f042ad5-9f39-4875-ac0c-cd86d4af4df2', 21, 'James', 'true', 'Kelly', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('573e766d-ec64-477d-b134-7c6893ce74d1', 23, 'Jackie', 'true', 'Riley', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5edf415b-a764-452b-a458-7264a48dec05', 24, 'Timothy', 'true', 'Roberts', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3918312c-6d3b-4ea2-bd75-42712c246587', 22, 'James', 'true', 'Sandoval', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('320ba635-1a54-4f84-888b-bb85b362491a', 29, 'Jeffrey', 'true', 'Hernandez', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('36de72a9-7e47-4214-be78-82f6b1b08315', 36, 'Sean', 'true', 'Christensen', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a05aef2b-50cd-4e55-95d9-758c21f84167', 34, 'Shari', 'true', 'Stewart', 1,  '113e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('da23a273-7d79-4a86-abd9-fb901d3d9724', 29, 'Aaron', 'true', 'Mcgee', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('0d0e8cf7-1850-400b-9a29-2c462a43b711', 33, 'Andrea', 'true', 'Phillips', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b2c430d4-54d4-4a22-905b-506e9dd19e25', 31, 'Maria', 'true', 'Cunningham', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('08c077a2-1fba-44f5-bc9b-5c2fa6554c6e', 23, 'Amanda', 'true', 'Lang', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('8d58459a-b138-4cf4-9547-a07e95655606', 31, 'Megan', 'true', 'Hensley', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('393809d0-bd2d-43d0-8528-009b8c878c45', 25, 'Miranda', 'true', 'Mcdaniel', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('da401b48-efc0-40db-bae6-6bd9e9b16133', 30, 'Sandra', 'true', 'Ramirez', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('0975ac23-df86-4fbc-921c-2532a3f2561a', 40, 'Steven', 'true', 'Davis', 1,  '123e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4f18eafa-e5c9-40b4-97f4-0e3e3a953b02', 30, 'Gloria', 'true', 'Mann', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('af8b050d-8359-4021-b6c8-a16e32d25be6', 29, 'Kathleen', 'true', 'Thomas', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6e79dd63-2923-4660-8e68-e656c46f59c0', 28, 'Jose', 'true', 'Bailey', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ee26945d-a979-4a39-a7af-1658fa88bbee', 20, 'Robert', 'true', 'Webb', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5718c109-bad7-43fa-9e2c-7f33e2c0f08e', 21, 'Mary', 'true', 'Bryant', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('7e5bede9-2c04-45b8-bfde-3c23ba237005', 31, 'Douglas', 'true', 'Patterson', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('0ffd1015-7e15-4184-b14e-d3d6f5666724', 30, 'Sarah', 'true', 'Martin', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6f68e3ca-7101-4256-9eab-6fb1401de780', 31, 'Cindy', 'true', 'Castillo', 1,  '133e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('bdecfd12-0e4a-489c-8c8e-5058533d2980', 27, 'Nicholas', 'true', 'Newton', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('fd64108c-12d9-4aa0-a3ea-58e925b6d5ca', 26, 'Joseph', 'true', 'Mckee', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('11b9f0de-63d8-49e9-9214-f62df8055247', 19, 'Jeffery', 'true', 'Shepherd', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('110b52b7-3c20-4e35-8d6f-c2495374b7c5', 34, 'David', 'true', 'Bailey', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('46ef4e86-83b7-411f-981b-43c9e757ab70', 36, 'Diana', 'true', 'Mercado', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1937bdda-2d9a-4f42-8dfa-65a8a2a2d76a', 39, 'Andrew', 'true', 'Taylor', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('fceb4027-2044-4ca1-8b08-263afdfb892c', 31, 'Kristen', 'true', 'Reynolds', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1daab53a-9dae-4232-a9c8-9f91d043691a', 39, 'Dennis', 'true', 'Richardson', 1,  '143e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('7208402c-902f-4bf3-a3bd-3d956dc41cf8', 27, 'Jonathon', 'true', 'Reese', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('18fd538d-06b1-4612-a763-6bff201b5f0b', 31, 'Vanessa', 'true', 'Wood', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f34d8e8b-3e3e-41ed-9f15-46c0aeb553d3', 25, 'Theodore', 'true', 'Lowe', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d0a6729a-ff67-4aa9-bbfd-ecc83846004e', 26, 'Elizabeth', 'true', 'Ramirez', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('9ea813f4-3ecc-4760-8aa8-c5f43625bc92', 20, 'Mark', 'true', 'Estrada', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3a787912-21be-441f-98ec-b59f90bf4333', 18, 'Lauren', 'true', 'Mendoza', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3eb7c520-d661-4d7d-bd24-c8d329f214f5', 22, 'Anthony', 'true', 'Gray', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('dd8282a9-e72f-4853-b942-991ff87ab7b9', 25, 'Andrew', 'true', 'Dyer', 1,  '153e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('edb9f28a-a56b-43d6-8d8f-e70517bd4ae8', 23, 'Melissa', 'true', 'Harris', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('288969a6-99a5-4136-a0dd-c4c468a07ccf', 29, 'Kenneth', 'true', 'Jacobs', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b85f60d6-7399-4536-9fe9-cb35b7559554', 35, 'Katrina', 'true', 'Martinez', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('af0310c4-6e8e-4bb8-8a09-58910fe18351', 36, 'Patrick', 'true', 'Perez', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('cad83f21-8d3f-451c-b729-307821717dac', 23, 'Jacqueline', 'true', 'Davis', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ddefb9ec-58ab-46b5-ae60-e4ba6d0bc9d6', 40, 'Brett', 'true', 'Lucas', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3b6f85ae-4e85-4083-8103-5b44d6cc14e6', 19, 'Michael', 'true', 'Roberts', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('500da95c-c77c-4c8b-8848-31eecebe8eaf', 32, 'Vincent', 'true', 'Lopez', 1,  '163e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3cc076d9-67bf-49c5-83fb-27d62e912526', 39, 'Nathan', 'true', 'Howard', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('62865d62-f3b4-460e-a297-c58bf7b0a33f', 40, 'Brandy', 'true', 'Martin', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('8f471914-f1e9-4e3a-83f1-f54ef78dee26', 22, 'John', 'true', 'Ho', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('99fb790b-4a1c-425f-ab82-1219c7f4e404', 23, 'Cheryl', 'true', 'Contreras', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('74aa6ff2-3893-4d71-ae1f-62bb492f5dc2', 19, 'Heather', 'true', 'Tran', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3df1de9f-1aa3-4223-86d0-6e32e6be8f46', 24, 'Kevin', 'true', 'Reed', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('dc8996a3-a708-4945-90bf-d33cfecb2a4f', 32, 'Tracy', 'true', 'Norris', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c5c79741-c167-493d-92b5-d7f1a3dcd45b', 34, 'Debbie', 'true', 'Johnson', 1,  '173e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('28b0644f-f77f-4bab-93f0-4e504863172f', 18, 'Eric', 'true', 'Jennings', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('25f53e44-2eb9-409a-b669-6ff0db8b9d03', 34, 'Steven', 'true', 'Sullivan', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ad7c0a7d-50b6-4a3c-a4aa-d19eb828fdfe', 30, 'Lauren', 'true', 'Payne', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d98883ab-14cd-4812-bc19-a7974025f094', 40, 'Chris', 'true', 'Berg', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b327308e-a40b-4a45-8d93-e6fbb55319d8', 33, 'Lauren', 'true', 'Gonzalez', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1a6983ed-d7aa-40f1-befa-1eb3c795395a', 39, 'Melissa', 'true', 'Villarreal', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('8e0353de-d55a-4ddd-9860-e0b17d50f5a9', 18, 'Amy', 'true', 'Li', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3d4c5d7a-473e-47fd-a8ec-b13d9b3cc96a', 40, 'Norman', 'true', 'Foster', 1,  '183e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('78dfbe7e-09dd-4373-bcdb-bb81b47128a2', 36, 'David', 'true', 'Adams', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('900d490a-47df-4fe6-a1b0-1d3fb79865a2', 33, 'Kara', 'true', 'Lucas', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('dd4d3008-1875-4aaa-88dd-9dc54e7e86ab', 28, 'Daniel', 'true', 'Douglas', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('fe5716eb-fa9a-4123-b8a7-6649a4d7f7f5', 37, 'David', 'true', 'Thomas', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('7682258a-dab6-4c11-9fae-2c4c2780dcd6', 26, 'Erika', 'true', 'Boyd', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('296ef10a-aab1-4a0d-987e-686e41e9166f', 27, 'Justin', 'true', 'Harrison', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ceacea3f-ed87-4a03-bf42-f882a9d14c54', 34, 'Kevin', 'true', 'Calderon', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1e7ff6e7-a0f3-4df2-8e18-66088cfa806f', 32, 'Alyssa', 'true', 'Bowers', 1,  '193e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('2740629b-0e9b-4764-a469-793ce6fa2eb4', 23, 'Julie', 'true', 'Hall', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ce6fbe4b-3442-46a1-98b6-e48c8d16cf2c', 22, 'Kevin', 'true', 'Jones', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ce6feaa3-2bb6-461a-8c18-cebca4fc707d', 38, 'Jose', 'true', 'Benitez', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('8e095067-b772-42ce-acd3-67ec6d449d2b', 21, 'Monica', 'true', 'Washington', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('96c4bde1-eba0-4632-94a1-322448691fef', 20, 'Sarah', 'true', 'Lewis', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5420eea5-b8c3-479d-82b8-9a22d1c1fff8', 34, 'Kimberly', 'true', 'Welch', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6a3c585d-6a7c-487b-afef-498007e275e7', 18, 'Samantha', 'true', 'Graham', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3db99283-d1c4-4603-9f8d-6ae6464cd6fc', 26, 'Jeffrey', 'true', 'Bailey', 1,  '203e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('526f1cb5-e140-442f-b9bb-34258ec18a21', 23, 'Calvin', 'true', 'Mckinney', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ef9c44fb-fa6f-4be7-b829-67f2051d047c', 29, 'Alexander', 'true', 'Baird', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a3c33e43-2d68-4551-8e51-15aa72a33bc2', 24, 'Christopher', 'true', 'Chavez', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e97f031b-4cc5-4a78-bb10-dd447062d2d0', 40, 'Elizabeth', 'true', 'Garcia', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('522716be-aec3-4ada-8be8-a2a42f0ce22f', 34, 'John', 'true', 'Williams', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('06a2c135-7d88-4040-ae6d-c0a3341380f0', 19, 'David', 'true', 'Fernandez', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d0b2a936-ae78-4f26-a727-be2b6ebf3086', 32, 'Bill', 'true', 'Romero', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('00139a7d-e317-48ec-a42a-2098081aecfb', 25, 'Frank', 'true', 'Watson', 1,  '213e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b91d76b6-35bb-4d5d-ad6c-1818fb5bb063', 34, 'Michael', 'true', 'Woods', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d8c38e6f-3f67-4437-b27c-9996cbfca504', 19, 'Douglas', 'true', 'Potts', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('55698045-11e3-4b50-b495-96561d88b9c8', 25, 'Amy', 'true', 'Garcia', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5ac31241-d9d8-444a-9052-c575325e8f5a', 31, 'Claudia', 'true', 'Robinson', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f361550d-53f5-46f5-98f5-0db56808efd2', 34, 'Mark', 'true', 'Sanchez', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a6a7a879-bf2c-4200-a78f-b8476b8ac2a5', 28, 'Anna', 'true', 'Lewis', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5a444a1b-171b-414e-9ee7-dc19bef95c37', 19, 'Jennifer', 'true', 'Carroll', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1afe422c-590f-4041-8f7f-cbbda39f6dc1', 34, 'Billy', 'true', 'Fuller', 1,  '223e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('00e3a042-fa1e-4e96-aefe-b22764f19637', 18, 'Cynthia', 'true', 'Smith', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1690f152-2c5b-4f8c-a2c2-67d81ea0e2a7', 38, 'Brandon', 'true', 'Stephens', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f17bdf12-6088-4651-8870-55d35185994b', 35, 'Melissa', 'true', 'Chavez', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('03101311-638c-4992-971f-6dfaf303ac4c', 31, 'Martin', 'true', 'Mullins', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('94a33552-c77c-4e3a-b988-df13548cabae', 40, 'Timothy', 'true', 'White', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('90c17a69-2e35-4795-9a60-eeb38a3c5a37', 31, 'Marcia', 'true', 'Walker', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('db1b0dae-c691-4633-8b22-1461e57676ef', 26, 'Phillip', 'true', 'Taylor', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('12c20dd2-66a2-4aae-9d16-b5622307ab0e', 30, 'Courtney', 'true', 'Hawkins', 1,  '233e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('8062daf4-bf3a-47f0-89f6-e51bf994cd9e', 40, 'Erica', 'true', 'Lewis', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('25c69a76-2d29-4f9a-b6a6-f3e0bead27a7', 32, 'Luke', 'true', 'Reyes', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('87ab4de4-2755-43e9-8a0e-194d010be991', 33, 'Alicia', 'true', 'Wilkins', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e781e0cf-5dd6-4818-955a-b1cadf6b4ed2', 25, 'Matthew', 'true', 'Smith', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('abe8b64c-d930-4952-b4f0-e185de6eb90b', 29, 'Robert', 'true', 'Lewis', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('85a23ac9-fae6-4881-8890-dd36500df769', 40, 'Laura', 'true', 'Bailey', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('be73ae88-b124-4445-b038-e67e3ed06a62', 20, 'Justin', 'true', 'Moore', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('7664a1eb-115e-4f8e-81f9-6521ff41af57', 24, 'Michael', 'true', 'Simpson', 1,  '243e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f8ffe9ff-32a6-4e7e-807a-ccb347bf644f', 26, 'Eric', 'true', 'Singleton', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6e838a27-6613-4ecf-b31d-91dda8d0cc06', 18, 'Barbara', 'true', 'Moore', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ddd6e10b-5529-4b61-a71a-a9263ad41638', 25, 'Michael', 'true', 'Calderon', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d4c1f0e6-73fe-45d9-bf32-f75963a206a5', 23, 'Heather', 'true', 'Smith', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('deb1361f-f440-4524-82f0-964a12d0ece4', 19, 'Matthew', 'true', 'Jones', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('da686729-0872-4686-9d2a-399b867ca4be', 21, 'Jonathan', 'true', 'Proctor', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e8c5e513-86c7-418d-96a3-477074623082', 37, 'Laura', 'true', 'Boyd', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('60566511-9ae8-466f-bc81-7e5c6d1c5679', 19, 'Amy', 'true', 'Perry', 1,  '253e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c35adcfc-1948-42b1-a151-d012a58014d6', 24, 'Holly', 'true', 'Davies', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ef9f2a1d-4943-4946-9589-33815d8e9f3a', 25, 'Nancy', 'true', 'Garcia', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6d819308-8e7d-458e-8da9-1aa917d8f2c4', 34, 'Erica', 'true', 'Dillon', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c575b02f-510b-4012-b3c2-ca6dbff5a9ff', 20, 'Brian', 'true', 'Perry', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('9633b6c3-1614-4e7f-8fed-4be93ee6ada0', 33, 'Ruth', 'true', 'White', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('4dff17b4-5ac6-4dd4-acfd-f210a5dc6e60', 40, 'Shannon', 'true', 'Liu', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('61b6f39f-46c6-44ac-9391-e4e70091e7b6', 37, 'Lisa', 'true', 'Williams', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('92d3e092-2e34-4f5e-bae4-6d83ecb583c1', 26, 'Aaron', 'true', 'Chandler', 1,  '263e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('6e568c6f-5d23-406e-a4ac-742f4ff3b894', 19, 'Lisa', 'true', 'Orr', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('1b0a43f4-860c-4687-a40f-e585f224e73d', 31, 'Joseph', 'true', 'Williams', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3e813fc6-ef1d-4011-a49e-ae3a7a3562bf', 29, 'Chad', 'true', 'Walker', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b4682d2e-bb47-4cfd-b870-556dee9b3a0c', 30, 'Rhonda', 'true', 'Mills', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('a9ac3d45-e0d5-41fd-a8f5-3d5abc889c11', 18, 'Michael', 'true', 'Anderson', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e3f6013c-ac54-4926-96fe-3a84a71db674', 27, 'Christopher', 'true', 'Sullivan', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('dce4d1a6-7faa-4d44-95b0-2c857f1fe71d', 37, 'Bryan', 'true', 'Howe', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c955779f-9276-4e17-9cb8-da7ebba7e739', 30, 'Danielle', 'true', 'Lee', 1,  '273e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('32c253e2-245a-4f59-a8a4-32f0930e151a', 33, 'Tracy', 'true', 'Robertson', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('8109e9bc-675f-4f50-9a66-ddf3a787a6a4', 25, 'Brian', 'true', 'Thomas', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('66195dbd-5dd7-4a88-a22d-dda30731cfba', 34, 'Sherri', 'true', 'Garcia', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b4bef299-8663-496a-aa29-657b2e3ffed3', 38, 'Elijah', 'true', 'Hall', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e9bc8e6f-5658-4c14-b2b2-861c9cd18cb0', 36, 'Austin', 'true', 'Ortiz', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('29e3112b-f65d-4af6-b3b8-a9d49fd4f2dd', 21, 'Jessica', 'true', 'Alvarez', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('42426c8a-376c-40f9-97a1-0ff6e8bbebe8', 35, 'Anthony', 'true', 'Spence', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f20240a3-8355-4fad-b965-49015cda6014', 30, 'Kenneth', 'true', 'Ward', 1,  '283e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('ad8c8476-1052-4009-ba85-cf0d78be3853', 28, 'Richard', 'true', 'Williams', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f9bc7072-e11b-4864-8e0e-b505e996241e', 38, 'Devin', 'true', 'Mcgee', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('669976fe-5de8-492c-a0a6-959dc8e2d76f', 32, 'Jennifer', 'true', 'Cantu', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('9a5e0722-81ee-490e-af9e-a56bf80e2cb6', 31, 'Caitlin', 'true', 'Terrell', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('9903b0f9-d24c-4e82-aa9f-ef1ad04a7893', 26, 'Kristin', 'true', 'Johnson', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5b813178-7d34-442e-ac97-a167ca76d66a', 19, 'Jason', 'true', 'Mcdonald', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('2bcf6644-95ec-4afb-9a3c-b20cb88c5a8e', 37, 'Wendy', 'true', 'Taylor', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('d71aaec0-abd7-4b07-b67e-110316d1b15d', 30, 'Daniel', 'true', 'Perry', 1,  '293e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('0ae28d26-f1dc-4550-a901-af9cf400b261', 26, 'Eric', 'true', 'Roman', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('71ea97c2-9cec-4d28-88a1-81d25a06d786', 25, 'Jack', 'true', 'Robbins', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e5baf881-8c43-4b8a-b6dc-d6a06381cd02', 27, 'Daniel', 'true', 'Dorsey', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('30ab074d-e93a-40de-832a-3564d91e8f15', 19, 'Jennifer', 'true', 'Valdez', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('0709e6ae-59d0-4578-b5d8-560794ea2c40', 36, 'Melanie', 'true', 'Reed', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('3bc18159-5bd8-47f4-af1e-e59009835459', 22, 'Steven', 'true', 'Barker', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('79d11a67-0067-4af4-aba8-6b9a28f087f6', 20, 'Kathleen', 'true', 'Hanson', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('c38521ac-7757-4086-b8f5-0b68f21eca70', 18, 'Cynthia', 'true', 'Phillips', 1,  '303e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('be60b93e-de93-4987-82f8-482a3226b46a', 37, 'John', 'true', 'Jacobs', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f6e07396-0f56-41f7-b128-4a737174a26e', 21, 'Rachel', 'true', 'Parker', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('689c8c6a-6370-440b-8a7e-1b4e1e9051ce', 30, 'Zachary', 'true', 'Carr', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e3e93805-9568-403b-bbda-cd6083775896', 32, 'Dawn', 'true', 'Herman', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e42d5ec3-f6f6-4745-8944-b92397681160', 24, 'Jon', 'true', 'Whitney', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('faf0dcc5-23e7-41f1-889c-a60272024e47', 23, 'Laura', 'true', 'Scott', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('5e3baa40-4ade-4d63-a7d8-e56aba7c26a3', 36, 'Ronald', 'true', 'Olson', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('030ce00e-e317-41e5-b54a-975437271832', 25, 'Mary', 'true', 'Riddle', 1,  '313e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('74cedde3-cd20-431e-a2f0-f0dd406d7e60', 20, 'Eric', 'true', 'Johnson', 1,  '323e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('e58195f4-b48b-4b93-a67a-5c9abf252e53', 36, 'Jessica', 'true', 'Lamb', 1,  '323e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('42cdbc88-abe7-4357-b214-8d03df679c1e', 25, 'Candice', 'true', 'Coleman', 1,  '323e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('38e988ee-60f3-4235-90f4-6001bb376e19', 40, 'Amy', 'true', 'Johnson', 1,  '323e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('449b250c-459d-4511-8fc7-7c3649029b33', 30, 'Timothy', 'true', 'Reynolds', 1,  '323e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('b3ebaa57-a9e6-4031-8d0a-53cdc754370b', 34, 'Robert', 'true', 'Hunt', 1,  '323e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('f6dbca51-e0d1-4c04-8bbd-f523d50daec9', 19, 'Charles', 'true', 'Daniel', 1,  '323e4567-e89b-12d3-a456-426655440010');
INSERT INTO players (id, age, first_name, is_pro, last_name, version, team_id) VALUES ('908894f7-00ab-4f65-860d-13649c92a5a3', 28, 'Michelle', 'true', 'Patel', 1,  '323e4567-e89b-12d3-a456-426655440010');

-- Wstawianie danych do tabeli venues
INSERT INTO venues (id, address, court_number, version) VALUES ('123e4567-e89b-12d3-a456-426655440014', 'aleja Unii Lubelskiej 2', 1, 1);
INSERT INTO venues (id, address, court_number, version) VALUES ('223e4567-e89b-12d3-a456-426655440014', 'Milionowa 96A', 2, 1);
INSERT INTO venues (id, address, court_number, version) VALUES ('323e4567-e89b-12d3-a456-426655440014', 'Niciarniana 1/3', 3, 1);

COMMIT;
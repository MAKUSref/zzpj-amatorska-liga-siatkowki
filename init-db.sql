CREATE USER siatka_admin WITH PASSWORD 'siatka_admin';
CREATE USER siatka_mok WITH PASSWORD 'siatka_mok';
CREATE USER siatka_mzl WITH PASSWORD 'siatka_mzl';
CREATE USER siatka_auth WITH PASSWORD 'siatka_auth';

CREATE GROUP admins_group;
CREATE GROUP mok_group;
CREATE GROUP mzl_group;
ALTER GROUP admins_group ADD USER siatka_admin;
ALTER GROUP mok_group ADD USER siatka_mok;
ALTER GROUP mzl_group ADD USER siatka_mzl;

GRANT ALL ON ALL TABLES IN SCHEMA public TO siatka_admin;
REVOKE ALL ON DATABASE postgres FROM siatka_admin;

REVOKE ALL ON ALL TABLES IN SCHEMA public FROM siatka_mok;
GRANT ALL ON TABLE admins, captains, coaches, managers, referees, roles, accounts TO siatka_mok;
REVOKE ALL ON DATABASE postgres FROM siatka_mok;

REVOKE ALL ON ALL TABLES IN SCHEMA public FROM siatka_mzl;
GRANT ALL ON TABLE venues, timetables, players, teams, captains, sets, scores, scoreboards, rounds, round_teams, overall_team_scores, leagues, games, game_squads, days TO siatka_mzl;
REVOKE ALL ON DATABASE postgres FROM siatka_mzl;


CREATE GROUP auth_group;
ALTER GROUP auth_group ADD USER siatka_auth;

REVOKE ALL ON ALL TABLES IN SCHEMA public FROM siatka_auth;
GRANT ALL ON TABLE roles, accounts TO siatka_auth;
REVOKE ALL ON DATABASE postgres FROM siatka_auth;

GRANT ALL ON TABLE admins, captains, coaches, managers, referees, roles, accounts, teams, leagues TO siatka_mok;
GRANT ALL PRIVILEGES ON SCHEMA public TO admins_group;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admins_group;

INSERT INTO public.accounts (id, version, email, is_active, is_approved, is_blocked, lastname, locale, login, login_timestamp, name, password) VALUES ('d93b7ab9-da9b-490c-a096-5c48437cd3a9', 0, 'admin@als.pl', true, true, false, 'admin', 'en', 'admin', '2023-05-18 18:53:32.000000', 'admin', '$2a$12$t8yl1BfGAqp4ISTUByW/UuYk0/KPMvINjZAvxNbet1QKWyCPbQB4G');
INSERT INTO public.roles (id, version, role_type, account_id) VALUES ('5570ecea-3f89-41e3-9574-f4890b2e593f', 0, 'ADMIN', 'd93b7ab9-da9b-490c-a096-5c48437cd3a9');
INSERT INTO public.admins (id) VALUES ('5570ecea-3f89-41e3-9574-f4890b2e593f')



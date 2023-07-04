CREATE USER siatka_admin WITH PASSWORD 'siatka_admin';
CREATE USER siatka_mok WITH PASSWORD 'siatka_mok';
CREATE USER siatka_mzl WITH PASSWORD 'siatka_mzl';
CREATE USER siatka_auth WITH PASSWORD 'siatka_auth';

CREATE DATABASE siatka;

\c siatka

GRANT ALL ON SCHEMA public TO siatka_admin;







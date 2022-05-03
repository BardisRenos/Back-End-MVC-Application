#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';
  CREATE DATABASE $APP_DB_NAME;
  GRANT ALL PRIVILEGES ON DATABASE $APP_DB_NAME TO $APP_DB_USER;

  \connect $APP_DB_NAME $APP_DB_USER
  BEGIN;
  CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

      -- ===========================================================================
      -- candidate - Contains the following information for candidate db table
      -- ===========================================================================
      CREATE TABLE IF NOT EXISTS candidates (
          candidate_id            UUID NOT NULL CONSTRAINT PK_CANDIDATE PRIMARY KEY,
          name	                  VARCHAR(32),
          last_name               VARCHAR(64),
          dob     	              VARCHAR(24),
          address                 VARCHAR(256),
          city                    VARCHAR(64),
          country                 VARCHAR(64));
      CREATE INDEX candidate_index ON candidates (candidate_id, );

      INSERT INTO candidates (candidate_id, name, last_name, dob, address, city) VALUES
          (uuid_generate_v4(), 'Renos', 'Bardis', '15/10/1987', '78 bd du president wilson', 'Antibes'),
          (uuid_generate_v4(), 'Nikos', 'Papastathopoulos', '15/10/1982', '70 bd du president wilson', 'Nice'),
          (uuid_generate_v4(), 'Billy', 'Papadopoulos', '15/10/1981', '11 bd du president wilson', 'Cannes');

      -- ===========================================================================
      -- clients - Contains the following information for the clients db table
      -- ===========================================================================
      CREATE TABLE IF NOT EXISTS clients (
          client_id       UUID NOT NULL CONSTRAINT PK_CLIENT PRIMARY KEY,
          company_name    VARCHAR(64),
          sector          VARCHAR(64),
          city            VARCHAR(64),
          country         VARCHAR(64),
          company_size    INTEGER);

      INSERT INTO clients (client_id, company_name, sector, city, country, company_size) VALUES
          (uuid_generate_v4(), 'Atos', 'It Consultancy', 'Paris', 'France', 10000),
          (uuid_generate_v4(), 'Infotell', 'It Consultancy', 'Paris', 'France', 20000),
          (uuid_generate_v4(), 'Alten', 'It Consultancy', 'Nice', 'France', 17000);

      -- =================================================================================
      -- job_description - Contains the following information for job_description db table
      -- =================================================================================
      CREATE TABLE IF NOT EXISTS job_descriptions (
          job_description_id      UUID NOT NULL CONSTRAINT PK_JOB_DESCRIPTION PRIMARY KEY,
          client_id               UUID,
          company_name            VARCHAR(64),
          title                   VARCHAR(32),
          location                VARCHAR(32),
          description             VARCHAR(512),
          programming_language    VARCHAR(64),
          database_name           VARCHAR(64),
          framework               VARCHAR(64),
          level                   VARCHAR(64),
          is_open                 VARCHAR(64),
          number_needed           INTEGER,
          FOREIGN KEY (client_id) REFERENCES clients(client_id));

      INSERT INTO job_descriptions (job_description_id, company_name, title, location, description, programming_language, database_name, framework, level, is_open, number_needed) VALUES
          (uuid_generate_v4(), 'Atos', 'Java Developer', 'Lille', 'Java EE developer in Fintech', 'Java', 'SQL', 'Spring', 'Senior', 'Open', 1),
          (uuid_generate_v4(), 'Infotell', 'Java Developer', 'Paris', 'Java EE developer in Fintech', 'Java', 'SQL', 'Spring', 'Senior', 'Open', 1),
          (uuid_generate_v4(), 'Alten', 'Java Developer', 'Nice', 'Java EE developer in Fintech', 'Java', 'SQL', 'Spring', 'Senior', 'Open', 1);

      -- =================================================================================
      -- jobs_candidates - Contains the following information for jobs_candidates db table
      -- =================================================================================
      CREATE TABLE IF NOT EXISTS jobs_candidates (
          jobs_candidate_id UUID NOT NULL,
          job_id            UUID,
          candidate_id      UUID,
          applying_date     DATE NOT NULL,
          PRIMARY KEY (jobs_candidate_id, job_id, candidate_id),
          FOREIGN KEY (job_id) REFERENCES job_descriptions(job_description_id),
          FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id));
/*
      INSERT INTO jobs_candidates(jobs_candidate_id, job_id, candidate_id, applying_date) VALUES
          (uuid_generate_v4(), uuid_generate_v4(), uuid_generate_v4(), '2016-03-02 15:05:00'),
          (uuid_generate_v4(), uuid_generate_v4(), uuid_generate_v4(), '2017-03-02 14:05:00'),
          (uuid_generate_v4(), uuid_generate_v4(), uuid_generate_v4(), '2016-09-02 13:05:00');
*/

      -- =============================================================
      -- users - Contains the following information for users db table
      -- =============================================================
      CREATE TABLE IF NOT EXISTS users (
          user_id         UUID NOT NULL CONSTRAINT PK_USER PRIMARY KEY,
          first_name      VARCHAR(64),
          last_name       VARCHAR(64),
          email           VARCHAR(64),
          password        VARCHAR(64));

      INSERT INTO users(user_id, first_name, last_name, email, password) VALUES
          (uuid_generate_v4(), 'Renos', 'Bardis', 'renos@gmail.clom', '1234'),
          (uuid_generate_v4(), 'Omar', 'Matter', 'omar@gmail.clom', '2222'),
          (uuid_generate_v4(), 'Somona', 'Darna', 'somona@gmail.clom', '3333');

      -- ===============================================================================
      -- uploaded_files - Contains the following information for uploaded_files db table
      -- ===============================================================================
      CREATE TABLE IF NOT EXISTS uploaded_files (
          file_id        UUID NOT NULL CONSTRAINT PK_UPLOADED_FILE PRIMARY KEY,
          file_name      VARCHAR(64),
          file_type      VARCHAR(64),
          file_data      BYTEA);
  COMMIT;
EOSQL
DROP DATABASE IF EXISTS project_db;
CREATE DATABASE project_db;
USE project_db;

DROP TABLE IF EXISTS Task;
DROP TABLE IF EXISTS Sub_Project;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Manager;

CREATE TABLE Manager (
                         manager_id       INT PRIMARY KEY AUTO_INCREMENT,
                         manager_name     VARCHAR(50)  NOT NULL,
                         manager_username VARCHAR(50)  NOT NULL UNIQUE,
                         manager_password VARCHAR(50)  NOT NULL
);

CREATE TABLE Project (
                         project_id                     INT PRIMARY KEY AUTO_INCREMENT,
                         project_name                   VARCHAR(50)  NOT NULL,
                         project_description            VARCHAR(400),
                         project_estimated_time_minutes INT,
                         project_actual_time_minutes    INT,
                         project_manager_id             INT NOT NULL,
                         FOREIGN KEY (project_manager_id) REFERENCES Manager(manager_id)
);

CREATE TABLE Sub_Project (
                             sub_project_id                     INT PRIMARY KEY AUTO_INCREMENT,
                             sub_project_name                   VARCHAR(50)  NOT NULL,
                             sub_project_description            VARCHAR(400),
                             sub_project_estimated_time_minutes INT,
                             sub_project_actual_time_minutes    INT,
                             project_id                         INT NOT NULL,
                             FOREIGN KEY (project_id) REFERENCES Project(project_id)
);

CREATE TABLE Task (
                      task_id                     INT PRIMARY KEY AUTO_INCREMENT,
                      task_name                   VARCHAR(50)  NOT NULL,
                      task_description            VARCHAR(400),
                      task_estimated_time_minutes INT,
                      task_actual_time_minutes    INT,
                      sub_project_id              INT NOT NULL,
                      FOREIGN KEY (sub_project_id) REFERENCES Sub_Project(sub_project_id)
);
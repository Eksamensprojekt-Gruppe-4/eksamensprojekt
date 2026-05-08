DROP DATABASE IF EXISTS project_db;
CREATE DATABASE project_db;
USE project_db;

CREATE TABLE User (
                      user_id         INT PRIMARY KEY AUTO_INCREMENT,
                      user_name       VARCHAR(50)  NOT NULL,
                      user_role       ENUM('EMPLOYEE', 'MANAGER', 'ADMIN') NOT NULL,
                      user_experience ENUM('UNASSIGNED', 'INTERN', 'JUNIOR', 'SENIOR') NOT NULL,
                      user_username   VARCHAR(50)  NOT NULL UNIQUE,
                      user_password   VARCHAR(50)  NOT NULL
);

CREATE TABLE Project (
                         project_id                 INT PRIMARY KEY AUTO_INCREMENT,
                         project_name               VARCHAR(50)  NOT NULL,
                         project_description        VARCHAR(400),
                         project_start_date         DATE,
                         project_estimated_deadline DATE,
                         project_estimated_hours    DOUBLE,
                         project_actual_hours       DOUBLE,
                         owner_user_id              INT,
                         FOREIGN KEY (owner_user_id) REFERENCES User(user_id) ON DELETE SET NULL
);

CREATE TABLE project_assigned_user (
                                       project_assigned_user_id INT NOT NULL,
                                       project_id               INT NOT NULL,
                                       PRIMARY KEY (project_assigned_user_id, project_id),
                                       FOREIGN KEY (project_assigned_user_id) REFERENCES User(user_id) ON DELETE CASCADE,
                                       FOREIGN KEY (project_id)               REFERENCES Project(project_id) ON DELETE CASCADE
);

CREATE TABLE Sub_Project (
                             sub_project_id               INT PRIMARY KEY AUTO_INCREMENT,
                             sub_project_name             VARCHAR(50)  NOT NULL,
                             sub_project_description      VARCHAR(400),
                             sub_project_estimated_hours  DOUBLE,
                             sub_project_actual_hours     DOUBLE,
                             project_id                   INT NOT NULL,
                             FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE
);

CREATE TABLE Task (
                      task_id                 INT PRIMARY KEY AUTO_INCREMENT,
                      task_name               VARCHAR(50)  NOT NULL,
                      task_description        VARCHAR(400),
                      task_estimated_hours    DOUBLE,
                      task_actual_hours       DOUBLE,
                      user_id                 INT,
                      sub_project_id          INT NOT NULL,
                      FOREIGN KEY (user_id)        REFERENCES User(user_id) ON DELETE SET NULL,
                      FOREIGN KEY (sub_project_id) REFERENCES Sub_Project(sub_project_id) ON DELETE CASCADE
);
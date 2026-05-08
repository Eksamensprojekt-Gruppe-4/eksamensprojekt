USE project_db;

INSERT INTO User (user_name, user_role, user_experience, user_username, user_password) VALUES
                                                                                           ('Anders Nielsen',  'MANAGER',  'SENIOR',     'anders123',  'pass1234'),
                                                                                           ('Sofie Madsen',    'MANAGER',  'SENIOR',     'sofie953',   'pass5678'),
                                                                                           ('Lars Pedersen',   'EMPLOYEE', 'JUNIOR',     'lars271',    'pass9012'),
                                                                                           ('Maria Hansen',    'EMPLOYEE', 'INTERN',     'maria404',   'pass3456'),
                                                                                           ('Admin Bruger',    'ADMIN',    'UNASSIGNED', 'admin',      'admin1234');

INSERT INTO Project (project_name, project_description, project_start_date, project_estimated_deadline, project_estimated_hours, project_actual_hours, owner_user_id) VALUES
                                                                                                                                                                          ('Hjemmeside Redesign',  'Komplet redesign af virksomhedens hjemmeside',    '2025-01-10', '2025-06-30', 200.0, 85.5,  1),
                                                                                                                                                                          ('Intern HR System',     'Udvikling af internt HR-system til medarbejdere', '2025-02-01', '2025-08-15', 350.0, 120.0, 2),
                                                                                                                                                                          ('Mobil App v2',         'Anden version af mobil applikationen',            '2025-03-01', '2025-09-01', 500.0, 30.0,  1);

INSERT INTO project_assigned_user (project_assigned_user_id, project_id) VALUES
                                                                             (1, 1),
                                                                             (3, 1),
                                                                             (4, 1),
                                                                             (2, 2),
                                                                             (3, 2),
                                                                             (1, 3),
                                                                             (4, 3);

INSERT INTO Sub_Project (sub_project_name, sub_project_description, sub_project_estimated_hours, sub_project_actual_hours, project_id) VALUES
                                                                                                                                           ('UI Design',           'Design af alle sider og komponenter',     40.0, 35.0, 1),
                                                                                                                                           ('Backend API',         'REST API til hjemmesidens data',          80.0, 20.5, 1),
                                                                                                                                           ('Brugerstyring',       'Login, roller og rettigheder',            60.0, 10.0, 2),
                                                                                                                                           ('Lønmodul',            'Beregning og visning af løndata',         90.0, 0.0,  2),
                                                                                                                                           ('Onboarding Flow',     'Bruger onboarding skærme',                50.0, 5.0,  3);

INSERT INTO Task (task_name, task_description, task_estimated_hours, task_actual_hours, user_id, sub_project_id) VALUES
                                                                                                                     ('Lav wireframes',          'Skitser til alle sider',              8.0,  8.0,  4, 1),
                                                                                                                     ('Implementer navbar',      'Responsiv navigationsmenu',           6.0,  7.5,  3, 1),
                                                                                                                     ('Opret endpoints',         'CRUD endpoints til projekter',        20.0, 5.0,  3, 2),
                                                                                                                     ('Database design',         'ER-diagram og schema',                10.0, 10.0, 1, 2),
                                                                                                                     ('Login side',              'Autentificeringsskærm',               8.0,  4.0,  3, 3),
                                                                                                                     ('Rollebaseret adgang',     'Guard logik til Manager/Admin/User',  15.0, 0.0,  1, 3),
                                                                                                                     ('Lønberegner logik',       'Beregningsservice for løn',           25.0, 0.0,  2, 4),
                                                                                                                     ('Velkomstskærm',           'Første skærm nye brugere ser',        10.0, 5.0,  4, 5);
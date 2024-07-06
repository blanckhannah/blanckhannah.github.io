START TRANSACTION;

DROP TABLE IF EXISTS users, roles, student, teacher, behavior, student_teacher CASCADE;

CREATE TABLE student (
    student_id serial,
    first_name varchar(20) NOT NULL,
    last_name varchar(30) NOT NULL,
    CONSTRAINT PK_student PRIMARY KEY (student_id)
);

CREATE TABLE behavior (
    behavior_id serial,
	student_id int NOT NULL,
    action varchar(100) NOT NULL,
    time timestamp,
    CONSTRAINT PK_behavior PRIMARY KEY (behavior_id),
	CONSTRAINT FK_student FOREIGN KEY (student_id) REFERENCES student(student_id)
);

CREATE TABLE teacher (
    teacher_id serial,
    first_name varchar(20) NOT NULL,
    last_name varchar(30) NOT NULL,
    grade_level varchar(2),
    CONSTRAINT PK_teacher PRIMARY KEY (teacher_id)
);

CREATE TABLE student_teacher (
    student_id int NOT NULL,
    teacher_id int NOT NULL,
    CONSTRAINT PK_student_teacher PRIMARY KEY (student_id, teacher_id),
    CONSTRAINT FK_student_teacher_student FOREIGN KEY (student_id) REFERENCES student(student_id),
    CONSTRAINT FK_student_teacher_teacher FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);
	
CREATE TABLE users (
	username varchar(200) primary key,
	password varchar(200)
);

CREATE TABLE roles (
	username varchar(200) references users(username),
	rolename varchar(200)
);

INSERT INTO users (username, password) VALUES ('admin', '$2a$10$ObqfFHcPzIQk891pFvJVQ.896qq/Gs0Tgh7R.i3JP24VVAljc89Nq');
INSERT INTO users (username, password) VALUES ('teacher', '$2a$10$2ipU3O3PCY2KZZeEmtbdkumkd.MQCjJ6eLQVxWgACCp8O44iUu36K');
INSERT INTO roles (username, rolename) VALUES ('admin', 'ADMIN');
INSERT INTO roles (username, rolename) VALUES ('teacher', 'TEACHER');

INSERT INTO student (first_name, last_name) VALUES ('Rory', 'Black');
INSERT INTO student (first_name, last_name) VALUES ('Flynn', 'Black');
INSERT INTO student (first_name, last_name) VALUES ('Janessa', 'Martin');
INSERT INTO student (first_name, last_name) VALUES ('Carter', 'Martin');
INSERT INTO student (first_name, last_name) VALUES ('Royce', 'Brooks');
INSERT INTO student (first_name, last_name) VALUES ('Gabrielle', 'Taylor');
INSERT INTO student (first_name, last_name) VALUES ('Oliver', 'Lewis');
INSERT INTO student (first_name, last_name) VALUES ('Andy', 'Smith');
INSERT INTO student (first_name, last_name) VALUES ('Liam', 'Roberts');
INSERT INTO student (first_name, last_name) VALUES ('Robert', 'Andrews');
INSERT INTO student (first_name, last_name) VALUES ('Peyton', 'Michaels');
INSERT INTO student (first_name, last_name) VALUES ('Ava', 'White');
INSERT INTO student (first_name, last_name) VALUES ('Blake', 'Scott');
INSERT INTO student (first_name, last_name) VALUES ('Norah', 'Carlson');

INSERT INTO teacher (first_name, last_name, grade_level) VALUES ('Lindsey', 'Pingsterhaus', 'KG');
INSERT INTO teacher (first_name, last_name, grade_level) VALUES ('Samantha', 'Bean', '01');
INSERT INTO teacher (first_name, last_name, grade_level) VALUES ('Shelby', 'Kilpatrick', '02');
INSERT INTO teacher (first_name, last_name, grade_level) VALUES ('Catie', 'Parrott', '03');
INSERT INTO teacher (first_name, last_name, grade_level) VALUES ('April', 'Webb', '04');
INSERT INTO teacher (first_name, last_name, grade_level) VALUES ('Grace', 'Jackson', '05');

INSERT INTO student_teacher (student_id, teacher_id) VALUES (1, 1);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (2, 2);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (3, 1);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (4, 3);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (5, 4);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (6, 5);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (7, 2);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (8, 3);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (9, 4);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (10, 5);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (11, 1);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (12, 6);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (13, 6);
INSERT INTO student_teacher (student_id, teacher_id) VALUES (14, 4);	
	
INSERT INTO behavior (student_id, action) VALUES ('4', 'Hitting');
INSERT INTO behavior (student_id, action) VALUES ('4', 'Hitting');
INSERT INTO behavior (student_id, action) VALUES ('4', 'Hitting');
INSERT INTO behavior (student_id, action) VALUES ('4', 'Hitting');
INSERT INTO behavior (student_id, action) VALUES ('4', 'Eloped');
INSERT INTO behavior (student_id, action) VALUES ('4', 'Eloped');
INSERT INTO behavior (student_id, action) VALUES ('4', 'Eloped');
INSERT INTO behavior (student_id, action) VALUES ('4', 'Biting');
INSERT INTO behavior (student_id, action) VALUES ('3', 'Not in area');
INSERT INTO behavior (student_id, action) VALUES ('2', 'Inappropriate Language');
INSERT INTO behavior (student_id, action) VALUES ('2', 'Refusal');
INSERT INTO behavior (student_id, action) VALUES ('1', 'Verbal insult');
INSERT INTO behavior (student_id, action) VALUES ('1', 'Verbal threat');
INSERT INTO behavior (student_id, action) VALUES ('1', 'Inappropriate Language');
INSERT INTO behavior (student_id, action) VALUES ('3', 'Kicking');

COMMIT;



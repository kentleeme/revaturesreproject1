DROP SCHEMA IF EXISTS project0 CASCADE;

CREATE SCHEMA project0;

DROP TYPE IF EXISTS project0.role CASCADE;
CREATE TYPE project0.role AS ENUM ('Admin','Student','Instructor');

DROP TYPE IF EXISTS semester CASCADE;
CREATE TYPE semester AS ENUM ('FALL','SRPING','SUMMER');

DROP TYPE IF EXISTS enroll_status CASCADE;
CREATE TYPE enroll_status AS ENUM ('Enrolled','Dropped','Pending','Waitlist','');

DROP TABLE IF EXISTS project0.users CASCADE;
CREATE TABLE project0.users (
	id serial PRIMARY KEY,
	username varchar(20) UNIQUE NOT NULL CHECK (length(pw) >= 4),
	pw varchar(50) NOT NULL CHECK (length(pw) >= 6),
	role project0.role NOT NULL
);

DROP TABLE IF EXISTS project0.instructors CASCADE;
CREATE TABLE project0.instructors (
	instructor_id integer PRIMARY KEY REFERENCES project0.users (id),
	fname varchar (50) NOT NULL,
	lname varchar (50) NOT NULL,
	email varchar (200) UNIQUE,
	dep varchar (50)
);

ALTER TABLE project0.students 
ALTER fname DROP NOT NULL;

DROP TABLE IF EXISTS project0.courses CASCADE;
CREATE TABLE project0.courses (
	course_id serial PRIMARY KEY,
	coursecode varchar (6) UNIQUE NOT NULL,
	coursename varchar (100) NOT null,	
	sem semester,
	yr integer,
	capacity integer,                 
	instructor_id integer REFERENCES project0.instructors (instructor_id)
);

DROP TABLE IF EXISTS project0.students CASCADE;
CREATE TABLE project0.students (
	student_id integer PRIMARY KEY REFERENCES project0.users (id),
	fname varchar (50) NOT NULL,
	lname varchar (50) NOT NULL,
	email varchar (200) UNIQUE,
	deg varchar (100),
	gpa NUMERIC (3,2)
);

DROP TABLE IF EXISTS project0.enrollment CASCADE;
CREATE TABLE project0.enrollment (
	enrollment_id serial PRIMARY KEY,
	course_id integer REFERENCES project0.courses (course_id),
	student_id integer REFERENCES project0.students (student_id),
	status varchar (50) 
);

DROP TABLE IF EXISTS project0.assignments CASCADE;
CREATE TABLE project0.assignments (
	assignment_id serial PRIMARY KEY,
	course_id integer REFERENCES project0.courses (course_id),
	question TEXT,
	duedate timestamptz [0] 
);

DROP TABLE IF EXISTS project0.hwsubmissions CASCADE;
CREATE TABLE project0.hwsubmissions (
	submission_id serial PRIMARY KEY,
	assignment_id integer REFERENCES project0.assignments (assignment_id),
	cnt TEXT,
	submit_date timestamptz [0],
	grade integer
);
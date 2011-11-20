CREATE TABLE Courses(
	_id INTEGER NOT NULL,
	course_name_en VARCHAR(255) NOT NULL, 
	course_name_fr VARCHAR(255) NOT NULL, 
	course_name_es VARCHAR(255) NOT NULL,
	PRIMARY KEY (_id)
);

CREATE TABLE Programmes(
	_id INTEGER NOT NULL,
	course_id INTEGER NOT NULL,
	programme_name_en VARCHAR(255) NOT NULL, 
	programme_name_fr VARCHAR(255) NOT NULL, 
	programme_name_es VARCHAR(255) NOT NULL,
	PRIMARY KEY (_id),
	FOREIGN KEY (course_id) REFERENCES Courses(_id)
);

CREATE TABLE Parts(
	_id INTEGER NOT NULL,
	programme_id INTEGER NOT NULL,
	part_name_en VARCHAR(255) NOT NULL, 
	part_name_fr VARCHAR(255) NOT NULL, 
	part_name_es VARCHAR(255) NOT NULL,
	PRIMARY KEY (_id),
	FOREIGN KEY (programme_id) REFERENCES Programmes(_id)
);

CREATE TABLE Quizes(
	_id INTEGER NOT NULL,
	part_id INTEGER NOT NULL,
	updated_at DATE,
	PRIMARY KEY (_id),
	FOREIGN KEY (part_id) REFERENCES Parts(_id)
);

CREATE TABLE Questions(
	_id INTEGER NOT NULL,
	quiz_id INTEGER NOT NULL,
	question_en VARCHAR(1000) NOT NULL,
	question_fr VARCHAR(1000) NOT NULL,
	question_es VARCHAR(1000) NOT NULL,
	PRIMARY KEY (_id),
	FOREIGN KEY (quiz_id) REFERENCES Quizes(_id)
);

CREATE TABLE Options(
	_id INTEGER NOT NULL,
	quiz_id INTEGER NOT NULL,
	question_id INTEGER NOT NULL,
	option_en VARCHAR(255) NOT NULL,
	option_fr VARCHAR(255) NOT NULL,
	option_es VARCHAR(255) NOT NULL,
	answer BOOLEAN,
	PRIMARY KEY (_id),
	FOREIGN KEY (question_id) REFERENCES Questions(_id)
);

CREATE TABLE Feedbacks(
	_id INTEGER NOT NULL,
	quiz_id INTEGER NOT NULL,	
	feedback_en VARCHAR(1000) NOT NULL,
	feedback_fr VARCHAR(1000) NOT NULL,
	feedback_es VARCHAR(1000) NOT NULL,
	PRIMARY KEY (_id),
	FOREIGN KEY (quiz_id) REFERENCES Quizes(_id)
);
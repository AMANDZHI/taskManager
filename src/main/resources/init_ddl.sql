CREATE TABLE user_tbl (
  id varchar(100) NOT NULL UNIQUE,
  name varchar(30) NOT NULL,
  login varchar(30) NOT NULL UNIQUE,
  password varchar(100) NOT NULL,
  role varchar(10) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE project_tbl (
  id varchar(100) NOT NULL UNIQUE,
  name varchar(30) NOT NULL UNIQUE,
  description varchar(50) NOT NULL,
  userId varchar(100) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES user_tbl(id)
);

CREATE TABLE task_tbl (
  id varchar(100) NOT NULL UNIQUE,
  name varchar(30) NOT NULL UNIQUE,
  description varchar(50) NOT NULL,
  projectId varchar(100) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (projectId) REFERENCES project_tbl(id)
);


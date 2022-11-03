CREATE DATABASE IF NOT EXISTS Test_UKEESS;

CREATE TABLE IF NOT EXISTS Test_UKEESS.Departments
(
    deptID   INTEGER AUTO_INCREMENT,
    deptName VARCHAR(80) UNIQUE,
    CONSTRAINT dept_pk PRIMARY KEY (deptID)
);

CREATE TABLE IF NOT EXISTS Test_UKEESS.Employees
(
    empID     INTEGER AUTO_INCREMENT,
    empName   VARCHAR(80),
    empActive BIT(1),
    emp_dpID  INTEGER,
    CONSTRAINT emp_pk PRIMARY KEY (empID),
    CONSTRAINT emp_fk FOREIGN KEY (emp_dpID) REFERENCES Test_UKEESS.Departments (deptID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Test_UKEESS.Users
(
    userID     INTEGER AUTO_INCREMENT,
    username   VARCHAR(80) UNIQUE ,
    password   VARCHAR(200),
    CONSTRAINT user_pk PRIMARY KEY (userID)
)
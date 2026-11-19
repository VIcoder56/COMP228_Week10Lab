CREATE DATABASE IF NOT EXISTS applications_db;

USE applications_db;

CREATE TABLE IF NOT EXISTS employmentApplications (
    applicationId INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(50) NOT NULL,
    address VARCHAR(250) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    educationalAttachment VARCHAR(50) NOT NULL,
    gender VARCHAR(50) NOT NULL,
    dateAvailable DATE NOT NULL,
    desiredPosition VARCHAR(50) NOT NULL,
    desiredSalary VARCHAR(50) NOT NULL,
    isLegal BOOLEAN NOT NULL,
    isRelative BOOLEAN NOT NULL,
    explanation VARCHAR(400) NOT NULL
);
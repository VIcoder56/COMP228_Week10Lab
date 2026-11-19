package org.example;

import java.time.LocalDate;

public class Application {
    private int applicationId;
    private String fullname;
    private String address;
    private String phone;
    private String email;
    private String educationAttachment;
    private String gender;
    private LocalDate dateAvailable;
    private String position;
    private String salary;
    private Boolean isLegal;
    private Boolean isRelative;
    private String explanation;

    public Application(
            String fullname, String address, String phone,
            String email, String educationAttachment, String gender, LocalDate dateAvailable,
            String position, String salary, Boolean isLegal, Boolean isRelative, String explanation) {
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.educationAttachment = educationAttachment;
        this.gender = gender;
        this.dateAvailable = dateAvailable;
        this.position = position;
        this.salary = salary;
        this.isLegal = isLegal;
        this.isRelative = isRelative;
        this.explanation = explanation;
    }

    public Application(int applicationId, String fullname, String address, String phone,
                       String email, String educationAttachment, String gender, LocalDate dateAvailable,
                       String position, String salary, Boolean isLegal, Boolean isRelative, String explanation) {
        this(fullname, address, phone, email, educationAttachment, gender, dateAvailable, position, salary, isLegal, isRelative, explanation);
        this.applicationId = applicationId;
    }

    public int getId() {
        return applicationId;
    }

    public String getFullname() {
        return fullname;
    }

    public Boolean getRelative() {
        return isRelative;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }

    public String getSalary() {
        return salary;
    }

    public String getAddress() {
        return address;
    }

    public String getEducationAttachment() {
        return educationAttachment;
    }

    public LocalDate getDateAvailable() {
        return dateAvailable;
    }

    public String getExplanation() {
        return explanation;
    }

    public Boolean getLegal() {
        return isLegal;
    }

    public String getPhone() {
        return phone;
    }
}

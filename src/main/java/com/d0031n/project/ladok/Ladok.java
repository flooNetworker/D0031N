package com.d0031n.project.ladok;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "ladok")
@SequenceGenerator(name = "ladok_sequence", sequenceName = "ladok_sequence", allocationSize = 1)
public class Ladok {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ladok_sequence")
    Integer id;

    String personNumber;
    String courseCode;
    String module;
    LocalDate date;
    String grade;

    public Ladok() {
    }

    public Ladok(String personNumber, String courseCode, String module, LocalDate date, String grade) {
        this.personNumber = personNumber;
        this.courseCode = courseCode;
        this.module = module;
        this.date = date;
        this.grade = grade;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getPersonNumber() {
        return personNumber;
    }
    public void setPersonNumber(String personNumber) {
        this.personNumber = personNumber;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
}
package com.d0031n.project.canvas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "canvas")
@SequenceGenerator(name = "canvas_sequence", sequenceName = "canvas_sequence", allocationSize = 1)
public class Canvas {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "canvas_sequence")
    Integer id;

    String username;
    String courseCode;
    String moduleCode;
    String canvasGrade;

    public Canvas() {
    }

    public Canvas(
        String username,
        String courseCode,
        String moduleCode,
        String canvasGrade) {
        this.username = username;
        this.courseCode = courseCode;
        this.moduleCode = moduleCode;
        this.canvasGrade = canvasGrade;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    public String getModuleCode() {
        return moduleCode;
    }
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }
    public String getCanvasGrade() {
        return canvasGrade;
    }
    public void setCanvasGrade(String canvasGrade) {
        this.canvasGrade = canvasGrade;
    }
}



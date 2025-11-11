package com.d0031n.project.epok;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "modules")
@SequenceGenerator(name = "module_sequence", sequenceName = "module_sequence", allocationSize = 1)
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_sequence")
    private Integer id;
    private String description;
    private String code;
    private String courseCode;

    public Module() {}

    public Module(String description, String code, String courseCode) {
        this(null, description, code, courseCode);
    }

    public Module(Integer id, String description, String code, String courseCode) {
        this.id = id;
        this.description = description;
        this.code = code;
        this.courseCode = courseCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", code='" + code + '\'' +
                ", courseCode='" + courseCode + '\'' +
                '}';
    }
}

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
    private String moduleDescription;
    private String moduleCode;
    private String courseCode;

    public Module() {}

    public Module(String moduleDescription, String moduleCode, String courseCode) {
        this(null, moduleDescription, moduleCode, courseCode);
    }

    public Module(Integer id, String moduleDescription, String moduleCode, String courseCode) {
        this.id = id;
        this.moduleDescription = moduleDescription;
        this.moduleCode = moduleCode;
        this.courseCode = courseCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
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
                ", moduleDescription='" + moduleDescription + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", courseCode='" + courseCode + '\'' +
                '}';
    }
}

package com.d0031n.project.controller;

import com.d0031n.project.service.StudentITSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping(path = "api/v1/students")
public class StudentITSController {

    private final StudentITSService studentITSService;

    @Autowired
    public StudentITSController(StudentITSService studentITSService) {
        this.studentITSService = studentITSService;
    }

    @GetMapping("{username}/personnumber")
    public ResponseEntity<String> getStudentPersonNumber(@PathVariable("username") String username) {
        String pn = studentITSService.getStudentPersonNumber(username);
        if ("Student not found".equals(pn)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pn);
    }
}
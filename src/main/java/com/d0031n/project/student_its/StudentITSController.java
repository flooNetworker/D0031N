package com.d0031n.project.student_its;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/studentdb")
public class StudentITSController {

    private final StudentITSService studentITSService;

    @Autowired
    public StudentITSController(StudentITSService studentITSService) {
        this.studentITSService = studentITSService;
    }

    @PostMapping("/students/by-usernames")
    public List<Student> getStudentsByUsernames(@RequestBody List<String> usernames) {
        return studentITSService.findStudentsByUsernames(usernames);
    }

    @GetMapping("/personnumber/{username}")
    public ResponseEntity<String> getStudentPersonNumberByUsername(@PathVariable("username") String username) {
        String pn = studentITSService.getPersonNumberByUsername(username);
        if ("Student not found".equals(pn)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pn);
    }
}
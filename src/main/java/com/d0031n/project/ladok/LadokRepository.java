package com.d0031n.project.ladok;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LadokRepository extends JpaRepository<Ladok, Integer> {
    Optional<Ladok> findByPersonNumberAndCourseCodeAndModule(String personNumber, String courseCode, String module);
}
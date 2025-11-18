package com.d0031n.project.student_its;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUsername(String username);

    List<Student> findAllByUsernameIn(List<String> usernames);
}

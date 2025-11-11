package com.d0031n.project.student_its;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    // A custom query to find a student by username
    @Query("SELECT s FROM Student s WHERE s.username = ?1")
    Optional<Student> findByUsername(String userName);
}

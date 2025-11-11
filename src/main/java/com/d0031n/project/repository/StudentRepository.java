package com.d0031n.project.repository;

import com.d0031n.project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    // A custom query to find an student personnumber by username
    @Query("SELECT s FROM Student s WHERE s.userName = ?1")
    Optional<Student> findByUserName(String userName);
}

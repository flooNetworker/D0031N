package com.d0031n.project.epok;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EpokRepository extends JpaRepository<Module, Integer> {
    List<Module> findByCourseCode(String courseCode);
}
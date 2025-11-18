package com.d0031n.project.canvas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CanvasRepository extends JpaRepository<Canvas, Integer> {

    @Query("SELECT DISTINCT c.courseCode FROM Canvas c ORDER BY c.courseCode")
    List<String> findDistinctCourseCodes();

    List<Canvas> findByCourseCodeAndModuleCode(String courseCode, String moduleCode);

    @Query("SELECT DISTINCT c.username FROM Canvas c WHERE c.courseCode = :courseCode AND c.moduleCode = :moduleCode")
    List<String> findUsernamesByCourseCodeAndModuleCode(@Param("courseCode") String courseCode, @Param("moduleCode") String moduleCode);
}
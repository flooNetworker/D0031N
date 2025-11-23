package com.d0031n.project.canvas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

// Repository (@Repository)
// Pratar direkt med databasen
// Spring genererar SQL-kod automatiskt baserat på metodnamn eller custom queries för komplex sökningar
@Repository
public interface CanvasRepository extends JpaRepository<Canvas, Integer> {

    @Query("SELECT DISTINCT c.courseCode FROM Canvas c ORDER BY c.courseCode")
    List<String> findDistinctCourseCodes();

    List<Canvas> findByCourseCodeAndModuleCode(String courseCode, String moduleCode);

    @Query("SELECT DISTINCT c.username FROM Canvas c WHERE c.courseCode = :courseCode AND c.moduleCode = :moduleCode")
    List<String> findUsernamesByCourseCodeAndModuleCode(@Param("courseCode") String courseCode, @Param("moduleCode") String moduleCode);
}
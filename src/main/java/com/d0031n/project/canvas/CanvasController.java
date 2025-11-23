package com.d0031n.project.canvas;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Controller (@RestController)
// Tar emot HTTP-förfrågningar från webbläsaren
// Använder @GetMapping för att definiera vilken URL som hanteras
@RestController
@RequestMapping("api/v1/canvas")
public class CanvasController {

    private final CanvasService canvasService;

// @Autowired - Dependency Injection
// Spring skapar automatiskt objekten vi behöver och "injicerar" dem i konstruktorn.
// Vi behöver inte skriva new CanvasService(), spring löser det
    @Autowired
    public CanvasController(CanvasService canvasService) {
        this.canvasService = canvasService;
    }

    @GetMapping("/coursecodes")
    public ResponseEntity<List<String>> getCourseCodes() {
        List<String> courseCodes = canvasService.getDistinctCourseCodes();
        return ResponseEntity.ok(courseCodes);
    }

    @GetMapping("/usernames/{courseCode}/{moduleCode}")
    public ResponseEntity<List<String>> getUsernames(@PathVariable String courseCode, @PathVariable String moduleCode) {
        List<String> usernames = canvasService.getUsernamesForModule(courseCode, moduleCode);
        return ResponseEntity.ok(usernames);
    }

    @GetMapping("/grades/{courseCode}/{moduleCode}")
    public ResponseEntity<Map<String, String>> getGrades(
            @PathVariable String courseCode,
            @PathVariable String moduleCode) {
        Map<String, String> grades = canvasService.getGradesForModule(courseCode, moduleCode);
        return ResponseEntity.ok(grades);
    }
}
package com.d0031n.project.epok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/epokdb")
public class EpokController {

    private final EpokService epokService;

    @Autowired
    public EpokController(EpokService epokService) {
        this.epokService = epokService;
    }

    @GetMapping("/modules/{courseCode}")
    public ResponseEntity<List<Map<String, String>>> getModulesByCourseCode(@PathVariable("courseCode") String courseCode) {
        List<Map<String, String>> modules = epokService.getModulesByCourseCode(courseCode)
                .stream()
                .map(module -> Map.of(
                        "code", module.getCode(),
                        "description", module.getDescription()
                ))
                .collect(Collectors.toList());

        if (modules.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(modules);
        }
    }
}
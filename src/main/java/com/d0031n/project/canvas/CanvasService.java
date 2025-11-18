package com.d0031n.project.canvas;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CanvasService {

    private final RestTemplate restTemplate;
    private final CanvasRepository canvasRepository;
    private final String studentItsBaseUrl = "http://localhost:8080/api/v1/studentdb";
    private final String epokBaseUrl = "http://localhost:8080/api/v1/epokdb";

    @Autowired
    public CanvasService(RestTemplate restTemplate, CanvasRepository canvasRepository) {
        this.restTemplate = restTemplate;
        this.canvasRepository = canvasRepository;
    }

    public Map<String, Object> getStudentAndCourseData(String username, String courseCode) {
        String studentUrl = studentItsBaseUrl + "/personnumber/" + username;
        ResponseEntity<String> studentResponse = restTemplate.getForEntity(studentUrl, String.class);
        String personNumber = studentResponse.getBody();

        String epokUrl = epokBaseUrl + "/modules/" + courseCode;
        ResponseEntity<List<Map<String, String>>> epokResponse = restTemplate.exchange(
                epokUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        List<Map<String, String>> modules = epokResponse.getBody();

        Map<String, Object> response = new HashMap<>();
        response.put("personNumber", personNumber);
        response.put("modules", modules);

        return response;
    }

    public List<String> getDistinctCourseCodes() {
        return canvasRepository.findDistinctCourseCodes();
    }

    public List<String> getUsernamesForModule(String courseCode, String moduleCode) {
        return canvasRepository.findUsernamesByCourseCodeAndModuleCode(courseCode, moduleCode);
    }

    public Map<String, String> getGradesForModule(String courseCode, String moduleCode) {
        List<Canvas> canvasEntries = canvasRepository.findByCourseCodeAndModuleCode(courseCode, moduleCode);
        return canvasEntries.stream()
                .collect(Collectors.toMap(Canvas::getUsername, Canvas::getCanvasGrade));
    }
}
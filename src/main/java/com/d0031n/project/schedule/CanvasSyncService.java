package com.d0031n.project.schedule;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CanvasSyncService {

    // URL till API och API token läses från application.properties
    @Value("${canvas.api.url}")
    private String canvasUrl;

    @Value("${canvas.api.token}")
    private String canvasToken;

    private final RestTemplate restTemplate;

    @Autowired
    public CanvasSyncService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createEvent(CanvasCalendarEvent event) {
        String url = canvasUrl + "/calendar_events";

        // När vi skickar data till Canvas API för att skapa en kalenderhändelse,
        // behöver vi ofta "wrappa" parametrarna. För att skapa en kalenderhändelse
        // använder vi antingen form-data med nycklar som "calendar_event[title]" eller json objekt { "calendar_event": { ... } }
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("calendar_event", event);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(canvasToken);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            System.out.println("Event skickat till Canvas: " + event.getTitle() + " [Status: " + response.getStatusCode() + "]");
        } catch (Exception e) {
            System.err.println("Fel när event skickades till Canvas: " + e.getMessage());
        }
    }
}
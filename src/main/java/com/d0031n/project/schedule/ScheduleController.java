package com.d0031n.project.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleIntegrationService integrationService;

    @Autowired
    public ScheduleController(ScheduleIntegrationService integrationService) {
        this.integrationService = integrationService;
    }

// Synkar TimeEdit schema (sparad i schema.json) till Canvas genom API anrop
    @PostMapping("/sync")
    public ResponseEntity<String> syncTimeEditToCanvas() {
        String result = integrationService.syncSchedule();
        return ResponseEntity.ok(result);
    }
}
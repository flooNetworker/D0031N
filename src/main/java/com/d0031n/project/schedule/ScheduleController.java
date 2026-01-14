package com.d0031n.project.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/schedule")
public class ScheduleController {

    private final ScheduleIntegrationService scheduleIntegrationService;

    @Autowired
    public ScheduleController(ScheduleIntegrationService scheduleIntegrationService) {
        this.scheduleIntegrationService = scheduleIntegrationService;
    }

    // GET: Hämtar schemat från TimeEdit och mappar till CanvasCalendarEvent utan att skicka till Canvas
    @GetMapping("/preview")
    public ResponseEntity<List<CanvasCalendarEvent>> previewSchedule() {
        return ResponseEntity.ok(scheduleIntegrationService.getDraftEvents());
    }

    // POST: Tar emot korrigerad CanvasCalendarEvent-lista från UI och skickar till Canvas
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmSync(@RequestBody List<CanvasCalendarEvent> events) {
        return ResponseEntity.ok(scheduleIntegrationService.syncEvents(events));
    }
}
package com.d0031n.project.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ScheduleIntegrationService {

    private final TimeEditService timeEditService;
    private final CanvasSyncService canvasSyncService;


    // Vart kalenderhändelserna ska hamna läses från application.properties
    @Value("${canvas.calendar.context}")
    private String targetContextCode;

    @Autowired
    public ScheduleIntegrationService(TimeEditService timeEditService, CanvasSyncService canvasSyncService) {
        this.timeEditService = timeEditService;
        this.canvasSyncService = canvasSyncService;
    }

    public List<CanvasCalendarEvent> getDraftEvents() {
        List<TimeEditReservation> reservations = timeEditService.fetchSchedule();
        
        return reservations.stream()
                .map(this::mapToCanvasEvent)
                .toList();
    }

    public String syncEvents(List<CanvasCalendarEvent> events) {
        int count = 0;
        for (CanvasCalendarEvent event : events) {
            canvasSyncService.createEvent(event);
            count++;
        }
        return "Synkronisering klar! Skickade " + count + " events till Canvas (i kalender " + targetContextCode + ").";
    }

    private CanvasCalendarEvent mapToCanvasEvent(TimeEditReservation res) {
        // Hämta data från kolumnerna
        // Index 0: Typ (Lektion)
        // Index 1: Sal/Plats (Zoom, A1301...)
        // Index 2: Kurskod
        // Index 3: Kursnamn
        // Index 4: --
        // Index 5: Lärare
        // Index 6: --
        // Index 7: Kommentar
        // Index 8: Zoom-länk
        // Index 9: Campus
        // Index 10: Övrig text
        String title = !res.getColumns().isEmpty() ? res.getColumns().get(0) : "Lektion";
        String location = res.getColumns().size() > 1 ? res.getColumns().get(1) : "";
        
        StringBuilder description = new StringBuilder("Importerad från TimeEdit.\n");
        
        List<String> columns = res.getColumns();
        if (columns.size() > 2) description.append("Kurskod: ").append(columns.get(2)).append("\n");
        if (columns.size() > 3) description.append("Kursnamn: ").append(columns.get(3)).append("\n");
        if (columns.size() > 5) description.append("Lärare: ").append(columns.get(5)).append("\n");
        if (columns.size() > 7) description.append("Kommentar: ").append(columns.get(7)).append("\n");
        if (columns.size() > 8) description.append("Zoom-länk: ").append(columns.get(8)).append("\n");
        if (columns.size() > 9) description.append("Campus: ").append(columns.get(9)).append("\n");
        if (columns.size() > 10) description.append("Övrig text: ").append(columns.get(10));
        
        String startAt = res.getStartDate() + "T" + res.getStartTime() + ":00";
        String endAt = res.getEndDate() + "T" + res.getEndTime() + ":00";

        return new CanvasCalendarEvent(
            targetContextCode,
            title,
            startAt,
            endAt,
            location,
            description.toString()
        );
    }
}
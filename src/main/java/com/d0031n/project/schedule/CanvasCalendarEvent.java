package com.d0031n.project.schedule;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CanvasCalendarEvent {
    
    @JsonProperty("context_code")
    private String contextCode;
    
    private String title;
    
    @JsonProperty("start_at")
    private String startAt;
    
    @JsonProperty("end_at")
    private String endAt;
    
    @JsonProperty("location_name")
    private String locationName;
    
    @JsonProperty("description")
    private String description;

    public CanvasCalendarEvent(String contextCode, String title, String startAt, String endAt, String locationName, String description) {
        this.contextCode = contextCode;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.locationName = locationName;
        this.description = description;
    }

    public String getContextCode() { return contextCode; }
    public String getTitle() { return title; }
    public String getStartAt() { return startAt; }
    public String getEndAt() { return endAt; }
    public String getLocationName() { return locationName; }
    public String getDescription() { return description; }
}
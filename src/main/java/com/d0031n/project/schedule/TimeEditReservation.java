package com.d0031n.project.schedule;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Mappar JSON-svaret från TimeEdit (hårdkodat i schema.json) 
// schema.json hämtades härifrån https://cloud.timeedit.net/ltu/web/schedule1/ri198653575796QQ54ZZ6660Q5yYW5Q056706007.html med ändelsen json
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimeEditReservation {
    
    private String id;
    
    @JsonProperty("startdate")
    private String startDate;
    
    @JsonProperty("starttime")
    private String startTime;
    
    @JsonProperty("enddate")
    private String endDate;
    
    @JsonProperty("endtime")
    private String endTime;
    
    // TimeEdit returnerar en array med strängar för detaljer
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
    private List<String> columns;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    
    public List<String> getColumns() { return columns; }
    public void setColumns(List<String> columns) { this.columns = columns; }
}
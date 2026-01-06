package com.d0031n.project.schedule;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Service
public class TimeEditService {

    public List<TimeEditReservation> fetchSchedule() {
        System.out.println("Läser schema från filen schema.json...");

        try {
            ClassPathResource resource = new ClassPathResource("schema.json");
            InputStream inputStream = resource.getInputStream();

            ObjectMapper mapper = new ObjectMapper();

            // Läs hela JSON filen
            JsonNode root = mapper.readTree(inputStream);

            // Hämta reservations-arrayen
            JsonNode reservationsNode = root.get("reservations");

            if (reservationsNode == null || !reservationsNode.isArray()) {
                System.err.println("Kunde inte hitta 'reservations'-listan i JSON-filen.");
                return Collections.emptyList();
            }

            // Mappa om JSON-arrayen till en array av TimeEditReservation-objekt
            List<TimeEditReservation> reservations = mapper.convertValue(
                reservationsNode, 
                new TypeReference<List<TimeEditReservation>>(){}
            );

            System.out.println("Lyckades läsa in " + reservations.size() + " bokningar från filen");

            return reservations;

        } catch (IOException e) {
            System.err.println("Fel vid läsning av schema.json: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
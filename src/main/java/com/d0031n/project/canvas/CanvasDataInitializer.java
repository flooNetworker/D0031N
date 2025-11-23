package com.d0031n.project.canvas;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class CanvasDataInitializer implements CommandLineRunner {

    private final CanvasRepository canvasRepository;

    public CanvasDataInitializer(CanvasRepository canvasRepository) {
        this.canvasRepository = canvasRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (canvasRepository.count() == 0) {           
            List<Canvas> canvasEntries = new ArrayList<>();

           // D0023E: inlämningsuppgifter (003)
            canvasEntries.add(new Canvas("annpon-3", "D0023E", "003", "G"));
            canvasEntries.add(new Canvas("isahog-3", "D0023E", "003", "VG"));
            canvasEntries.add(new Canvas("amiaga-3", "D0023E", "003", "G"));

            // D0023E: projekt (004)
            canvasEntries.add(new Canvas("annpon-3", "D0023E", "004", "G"));
            canvasEntries.add(new Canvas("isahog-3", "D0023E", "004", "VG"));
            canvasEntries.add(new Canvas("amiaga-3", "D0023E", "004", "G"));

            // D0031N - projekt (006)
            canvasEntries.add(new Canvas("emeasx-9", "D0031N", "006", "G"));
            canvasEntries.add(new Canvas("annpon-3", "D0031N", "006", "G"));
            canvasEntries.add(new Canvas("isahog-3", "D0031N", "006", "VG"));
            canvasEntries.add(new Canvas("amiaga-3", "D0031N", "006", "G"));

            // D0031N - inlämning (005)
            canvasEntries.add(new Canvas("emeasx-9", "D0031N", "005", "VG"));
            canvasEntries.add(new Canvas("annpon-3", "D0031N", "005", "G"));
            canvasEntries.add(new Canvas("isahog-3", "D0031N", "005", "U"));
            canvasEntries.add(new Canvas("amiaga-3", "D0031N", "005", "G"));

            canvasRepository.saveAll(canvasEntries);
            System.out.println("Inserted sample canvas entries for all students into canvasdb: " + canvasRepository.findAll());
        }
    }
}
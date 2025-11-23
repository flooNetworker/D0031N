package com.d0031n.project.ladok;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LadokService {

    private final LadokRepository ladokRepository;

    @Autowired
    public LadokService(LadokRepository ladokRepository) {
        this.ladokRepository = ladokRepository;
    }

    public Ladok regResult(String personNumber, String courseCode, String module, LocalDate date, String grade) {
        Optional<Ladok> existingEntry = ladokRepository.findByPersonNumberAndCourseCodeAndModule(personNumber, courseCode, module);
        if (existingEntry.isPresent()) {
            throw new IllegalStateException("Studenten har redan inlagt betyg i ladok för den här modulen");
        }
        Ladok ladok = new Ladok(personNumber, courseCode, module, date, grade);
        return ladokRepository.save(ladok);

    }
}
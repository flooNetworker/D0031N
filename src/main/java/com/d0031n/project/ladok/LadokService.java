package com.d0031n.project.ladok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LadokService {

    private final LadokRepository ladokRepository;

    @Autowired
    public LadokService(LadokRepository ladokRepository) {
        this.ladokRepository = ladokRepository;
    }

    public Ladok regResult(String personNumber, String courseCode, String module, LocalDate date, String grade) {
        Ladok ladok = new Ladok(personNumber, courseCode, module, date, grade);
        return ladokRepository.save(ladok);

    }
}
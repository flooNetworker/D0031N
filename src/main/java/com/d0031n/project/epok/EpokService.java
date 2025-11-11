package com.d0031n.project.epok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpokService {

    private final EpokRepository epokRepository;

    @Autowired
    public EpokService(EpokRepository epokRepository) {
        this.epokRepository = epokRepository;
    }

    public List<Module> getModulesByCourseCode(String courseCode) {
        return epokRepository.findByCourseCode(courseCode);
    }
}
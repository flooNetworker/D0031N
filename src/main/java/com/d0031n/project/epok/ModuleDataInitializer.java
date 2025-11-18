package com.d0031n.project.epok;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@Profile("!test")
public class ModuleDataInitializer implements CommandLineRunner {

    private final EpokRepository epokRepository;

    public ModuleDataInitializer(EpokRepository epokRepository) {
        this.epokRepository = epokRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (epokRepository.count() == 0) {
            var modules = List.of(
                    new Module("inlämningsuppgifter", "inlämning 1", "005", "D0031N"),
                    new Module("inlämningsuppgifter", "inlämning 2", "005", "D0031N"),
                    new Module("inlämningsuppgifter", "inlämning 3", "005", "D0031N"),
                    new Module("inlämningsuppgifter", "inlämning 4", "005", "D0031N"),
                    new Module("projekt", "projektuppgift A", "006", "D0031N"),
                    new Module("inlämningsuppifter", "uppgift 1.a", "003", "D0023E"),
                    new Module("inlämningsuppgifter", "uppgift 1.b", "003", "D0023E"),
                    new Module("inlämningsuppgifter", "uppgift 1.c", "003", "D0023E"),
                    new Module("projekt", "projektuppgift inviduell", "004", "D0023E")
            );
            epokRepository.saveAll(modules);
            System.out.println("Inserted sample modules into epokdb: " + epokRepository.findAll());
        }
    }
}
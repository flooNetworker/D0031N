package com.d0031n.project.student_its;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("!test")
public class StudentDataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public StudentDataInitializer(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (studentRepository.count() == 0) {
            var students = List.of(
                new Student("Mimmi Sandström", "emeasx-9", "18800101-1234"),
                new Student("Anna Ponga", "annpon-3", "18820202-2345"),
                new Student("Isak Högström", "isahog-3", "18830303-3456"),
                new Student("Maria Granath", "amiaga-3", "18840404-4567")
            );
            studentRepository.saveAll(students);
            System.out.println("Inserted sample students into studentITSdb: " + studentRepository.findAll());
        }
    }
}
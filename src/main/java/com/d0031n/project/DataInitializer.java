package com.d0031n.project;

import com.d0031n.project.model.Student;
import com.d0031n.project.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public DataInitializer(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (studentRepository.count() == 0) {
            var students = List.of(
                new Student("Mimmi Sandström", "emeasx-9", "19900101-1234"),
                new Student("Anna Ponga", "annpon-3", "19920202-2345"),
                new Student("Isak Högström", "isahog-3", "19930303-3456"),
                new Student("Maria Granath", "amiaga-3", "19940404-4567")
            );
            studentRepository.saveAll(students);
            System.out.println("Inserted sample students: " + studentRepository.findAll());
        }
    }
}
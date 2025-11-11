package com.d0031n.demo;

import com.d0031n.demo.model.Student;
import com.d0031n.demo.repository.StudentRepository;
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
                new Student("Mimmi Sandström", "alicea", "19900101-1234"),
                new Student("Anna Ponga", "bertilb", "19920202-2345"),
                new Student("Isak Högström", "ceciliac", "19930303-3456"),
                new Student("Maria Granath", "davidad", "19940404-4567")
            );
            studentRepository.saveAll(students);
            System.out.println("Inserted sample students: " + studentRepository.findAll());
        }
    }
}
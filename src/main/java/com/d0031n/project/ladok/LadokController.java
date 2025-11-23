package com.d0031n.project.ladok;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/ladokdb")
public class LadokController {

    private final LadokService ladokService;

    @Autowired
    public LadokController(LadokService ladokService) {
        this.ladokService = ladokService;
    }

    @PostMapping("/ladok/{courseCode}/{personNumber}/{module}/{date}/{grade}")
    public ResponseEntity<String> regResult(
        @PathVariable String courseCode,
        @PathVariable String personNumber,
        @PathVariable String module,
        @PathVariable LocalDate date,
        @PathVariable String grade) {

        // Testa registrera resultat genom att skapa ladok objekt
        try {
            ladokService.regResult(personNumber, courseCode, module, date, grade);
            return ResponseEntity.ok("Registrerad");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.ok("Hindrad");
        }
    }

    @ExceptionHandler({org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class})
    public ResponseEntity<String> handleTypeMismatch(Exception ex) {
        return ResponseEntity.ok("Hindrad");
    }
}
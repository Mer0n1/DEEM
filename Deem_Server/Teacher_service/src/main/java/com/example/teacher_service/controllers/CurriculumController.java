package com.example.teacher_service.controllers;

import com.example.teacher_service.models.Class;
import com.example.teacher_service.services.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.teacher_service.util.ResponseValidator.getErrors;

@RestController
@RequestMapping("/curriculum")
public class CurriculumController {
    @Autowired
    private ClassService classService;

    @GetMapping("/getTwoWeek")
    public List<Class> getTwoWeek() {
        return classService.getClasses();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH', 'TEACHER')")
    @PostMapping("/addClass")
    public ResponseEntity<?> addClass(@RequestBody @Valid Class cl, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        classService.save(cl);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH', 'TEACHER')")
    @PostMapping("/deleteClass")
    public ResponseEntity<?> deleteClass(@RequestBody @Valid Class cl, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        classService.delete(cl);

        return ResponseEntity.ok().build();
    }


}

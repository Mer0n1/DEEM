package com.example.teacher_service.controllers;

import com.example.teacher_service.models.Class;
import com.example.teacher_service.services.ClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public void addClass(@RequestBody @Valid Class cl, BindingResult bindingResult) {
        System.out.println("addClass " + classService);

        if (bindingResult.hasErrors())
            return;

        classService.save(cl);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'HIGH', 'TEACHER')")
    @PostMapping("/deleteClass")
    public void deleteClass(@RequestBody @Valid Class cl, BindingResult bindingResult) {
        System.out.println("deleteClass");

        if (bindingResult.hasErrors())
            return;

        classService.delete(cl);
    }


}

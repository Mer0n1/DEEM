package com.example.teacher_service.controllers;

import com.example.teacher_service.models.Teacher;
import com.example.teacher_service.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.teacher_service.util.ResponseValidator.getErrors;

@RestController
@RequestMapping("/teacher")
@PreAuthorize("hasAnyRole('ADMIN', 'HIGH')")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addTeacher")
    public ResponseEntity<?> addTeacher(@RequestBody @Valid Teacher teacher, //добавление нового учителя (форма)
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        teacherService.save(teacher);

        return ResponseEntity.ok().build();
    }

    @PostMapping("deleteTeacher")
    public ResponseEntity<?> deleteTeacher(@RequestBody @Valid Teacher teacher,
                              BindingResult bindingResult) { //увольнение

        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        teacherService.delete(teacher);

        return ResponseEntity.ok().build();
    }

    public void transferTeacher() {} //перевод в другую кафедру

    //назначение кураторства?


}

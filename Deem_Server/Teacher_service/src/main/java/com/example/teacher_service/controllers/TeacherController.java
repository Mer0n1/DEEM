package com.example.teacher_service.controllers;

import com.example.teacher_service.models.Teacher;
import com.example.teacher_service.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
@PreAuthorize("hasAnyRole('ADMIN', 'HIGH','TEACHER')")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addTeacher")
    public void addTeacher(@RequestBody @Valid Teacher teacher, //добавление нового учителя (форма)
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return;

        teacherService.save(teacher);
    }

    @PostMapping("deleteTeacher")
    public void deleteTeacher(@RequestBody @Valid Teacher teacher,
                              BindingResult bindingResult) { //увольнение

        if (bindingResult.hasErrors())
            return;

        teacherService.delete(teacher);
    }

    public void transferTeacher() {} //перевод в другую кафедру

    //назначение кураторства?

}

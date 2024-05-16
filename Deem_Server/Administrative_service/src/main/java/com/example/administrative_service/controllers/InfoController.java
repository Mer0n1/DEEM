package com.example.administrative_service.controllers;

import com.example.administrative_service.models.SubmissionForm;
import com.example.administrative_service.services.SubmissionStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    private SubmissionStoryService submissionStoryService;


    @GetMapping("/getSubmissionForms")
    public List<SubmissionForm> getSubmissionForms(@RequestParam("year") int year,
                                                   @RequestParam("month") int month) {
        System.out.println(year + " " + month);
        return submissionStoryService.getSubmissionForms(year, month);
    }

}

package com.example.messenger_service.controllers;

import com.example.messenger_service.models.Change;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/test")
public class TestController {

    @GetMapping("/test")
    public List<Change> get() {
        System.out.println("----------------");

        List<Change> list = new ArrayList<>();
        list.add(new Change("Text"));
        list.add(new Change("Test"));

        return list;
    }
}

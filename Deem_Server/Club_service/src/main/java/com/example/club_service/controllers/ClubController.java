package com.example.club_service.controllers;

import com.example.club_service.models.Club;
import com.example.club_service.services.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ClubService clubService;

    @GetMapping("/getClubs")
    public List<Club> getClubs() {
        System.out.println("/getClubs");
        return clubService.getClubs();
    }

    @PostMapping("/addClub")
    public void addClub() { //form //TODO
        //создание группы

        //создание клуба
    }

    @PostMapping("/deleteClub")
    public void deleteClub() { //form //TODO
        //удаление группы и каскадность (запрос в сервис групп где можно удалять только группы типа club)
    }

    @PostMapping("/testKafka")
    public void testKafka(@RequestParam("msgId") String msgId, @RequestParam("msg") String msg){
        kafkaTemplate.send("msg", msgId, msg);
    }
}

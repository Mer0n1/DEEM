package com.example.imageservice.controllers;

import com.google.common.io.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/image")
public class ImageController {

    @GetMapping("/getImageTest")
    public ResponseEntity<byte[]> getImageTest() throws IOException {
        System.out.println("getImage");
        File imageFile = new File(Resources.getResource("").getFile());
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    //to
    @PostMapping("/uploadProfile")
    public ResponseEntity<String> uploadProfile(
            @RequestParam("description") String description,
            @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok("Uploaded successfully.");
    }


    /** Вернуть 1 картинку (иконка аккаунта, группы, беседы)*/
    @GetMapping("/getImage")
    public void getImage(String name) {
        System.out.println("getImage");
    }

    /** Вернуть изображение 1 новости */
    @GetMapping("/getImagesNews")
    public void getImagesNews(String nameNews) {
    }

    /** Вернуть изображение 1 сообщения */
    @GetMapping("/getImagesMessage")
    public void getImagesMessage(String nameMessage) {
    }
}

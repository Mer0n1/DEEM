package com.example.imageservice.controllers;

import com.example.imageservice.models.Image;
import com.example.imageservice.services.ImageService;
import com.google.common.io.Resources;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

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

    @PostMapping("/addImages")
    public void addImages(@RequestBody @Valid List<Image> imgs,
                          BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return;

        imageService.initImages(imgs);
        imageService.saveAll(imgs);
    }


}

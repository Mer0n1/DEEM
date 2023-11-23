package com.example.imageservice.controllers;

import com.example.imageservice.models.DataImage;
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


    /** Вернуть изображение 1 сообщения */
    @GetMapping("/getImage")
    public Image getImage(String UUID) {
        System.out.println("getImage");
        DataImage image = imageService.getImage(UUID);
        Image imageResponse = null;

        if (image != null)
            imageResponse = image.getImage();

        return imageResponse;
    }

    @PostMapping("/addImages")
    public void addImages(@RequestBody @Valid List<DataImage> imgs,
                          BindingResult bindingResult) throws IOException {
        System.out.println("addImages");

        if (bindingResult.hasErrors())
            return;

        imageService.initImages(imgs);
        for (DataImage image : imgs)
            imageService.saveImage(image.getImage().getImgEncode(), image.getType());
        imageService.saveAll(imgs);
    }


}

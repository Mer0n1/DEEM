package com.example.imageservice.controllers;

import com.example.imageservice.models.*;
import com.example.imageservice.services.ImageService;
import com.example.imageservice.util.Validator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;
    @Autowired
    private Validator validator;

    @GetMapping("/getCount")
    public Integer getCount(@RequestParam("type") String type,
                            @RequestParam("id") Long id) {
        Integer result = -1;

        if (type.equals("news_image"))
            result = imageService.getCountImagesNews(id);
        else if (type.equals("message_image"))
            result = imageService.getCountImagesMessage(id);

        return result;
    }

    /** Вернуть изображение 1 сообщения */
    @GetMapping("/getImage")
    public Image getImage(@RequestParam("UUID") String UUID,
                          @RequestParam("type") String type) {
        return imageService.getImage(UUID, type);
    }

    @PostMapping("/addImageIcon")
    public ResponseEntity<?> addImageIcon(@RequestBody @Valid IconImage image,
                                       BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(getErrors(bindingResult));

        image.setPath(imageService.getPath("profile_icon") + principal.getName()+".png");

        IconImage image1 = imageService.getImageIcon(image.getUuid());
        if (image1 == null)  //если у пользователя нет иконки сохраняем
            imageService.saveIcon(image);

        imageService.saveImage(image.getImage().getImgEncode(), image.getPath());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/addImagesNews")
    public ResponseEntity<?> addImagesNews(@RequestBody List<NewsImage> imgs) throws IOException {

        if (validator.hasErrorsNewsImages(imgs))
            return ResponseEntity.badRequest().body("Некорректный запрос");

        imageService.initNewsImages(imgs);
        for (NewsImage image : imgs)
            imageService.saveImage(image.getImage().getImgEncode(), image.getPath());
        imageService.saveAllNewsImages(imgs);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/addImagesMessage")
    public ResponseEntity<?> addImagesMessage(@RequestBody List<MessageImage> imgs) throws IOException {
        if (validator.hasErrorsMessageImages(imgs))
            return ResponseEntity.badRequest().body("Некорректный запрос");

        imageService.initMessageImages(imgs);
        for (MessageImage image : imgs)
            imageService.saveImage(image.getImage().getImgEncode(), image.getPath());
        imageService.saveAllMessageImages(imgs);

        return ResponseEntity.ok().build();
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }
}

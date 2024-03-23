package com.example.imageservice.controllers;

import com.example.imageservice.models.*;
import com.example.imageservice.services.ImageService;
import com.example.imageservice.util.Validator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    public void addImageIcon(@RequestBody @Valid IconImage image,
                              BindingResult bindingResult, Principal principal) throws IOException {
        if (bindingResult.hasErrors())
            return;

        image.setPath(imageService.getPath("profile_icon") + principal.getName()+".png");

        IconImage image1 = imageService.getImageIcon(image.getUuid());
        if (image1 == null)  //если у пользователя нет иконки сохраняем
            imageService.saveIcon(image);

        imageService.saveImage(image.getImage().getImgEncode(), image.getPath());
    }

    @PostMapping("/addImagesNews")
    public void addImagesNews(@RequestBody List<NewsImage> imgs) throws IOException {
        if (validator.hasErrorsNewsImages(imgs))
            return;

        imageService.initNewsImages(imgs);
        for (NewsImage image : imgs)
            imageService.saveImage(image.getImage().getImgEncode(), image.getPath());
        imageService.saveAllNewsImages(imgs);
    }

    @PostMapping("/addImagesMessage")
    public void addImagesMessage(@RequestBody List<MessageImage> imgs) throws IOException {
        if (validator.hasErrorsMessageImages(imgs))
            return;

        imageService.initMessageImages(imgs);
        for (MessageImage image : imgs)
            imageService.saveImage(image.getImage().getImgEncode(), image.getPath());
        imageService.saveAllMessageImages(imgs);
    }

}

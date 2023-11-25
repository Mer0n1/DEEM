package com.example.imageservice.controllers;

import com.example.imageservice.models.*;
import com.example.imageservice.services.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /*@GetMapping("/getImageTest")
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
    }*/

    @GetMapping("/getCount")
    public Integer getCount(@RequestParam("type") String type,
                            @RequestParam("id") Long id) {
        System.out.println("getCount");
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
        System.out.println("getImage");
        return imageService.getImage(UUID, type);
    }

    @PostMapping("/addImageIcon")
    private void addImageIcon(@RequestBody @Valid IconImage image,
                              BindingResult bindingResult, Principal principal) throws IOException {
        System.out.println("addImageIcon");

        if (bindingResult.hasErrors())
            return;

        image.setPath(imageService.getPath("profile_icon") + principal.getName()+".png");
        imageService.saveImage(image.getImage().getImgEncode(),  image.getPath());
        imageService.saveIcon(image);
    }

    @PostMapping("/addImagesNews")
    private void addImagesNews(@RequestBody @Valid List<NewsImage> imgs,
                               BindingResult bindingResult) throws IOException {
        System.out.println("addImagesNews");

        if (bindingResult.hasErrors())
            return;

        imageService.initNewsImages(imgs);
        for (NewsImage image : imgs)
            imageService.saveImage(image.getImage().getImgEncode(), image.getPath());
        imageService.saveAllNewsImages(imgs);
    }

    @PostMapping("/addImagesMessage")
    private void addImagesMessage(@RequestBody @Valid List<MessageImage> imgs,
                               BindingResult bindingResult) throws IOException {
        System.out.println("addImagesMessage");

        if (bindingResult.hasErrors())
            return;

        imageService.initMessageImages(imgs);
        for (MessageImage image : imgs)
            imageService.saveImage(image.getImage().getImgEncode(), image.getPath());
        imageService.saveAllMessageImages(imgs);
    }

}

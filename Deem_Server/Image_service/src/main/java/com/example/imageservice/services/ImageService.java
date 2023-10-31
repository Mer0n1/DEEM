package com.example.imageservice.services;

import com.example.imageservice.models.Image;
import com.example.imageservice.repositories.ImageRepository;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;

    @Value("${PATH_ICONS}")
    private String PATH_ICONS;
    @Value("${PATH_NEWS}")
    private String PATH_NEWS;


    @Transactional
    public void save(Image img) {
        repository.save(img);
    }

    @Transactional
    public void saveAll(List<Image> imgs) {
        repository.saveAll(imgs);
    }


    public void getImage() {
        Image image = repository.findById(1).orElse(null);

        try {
            File file = new File(image.getPath());
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            String encodedString = Base64.toBase64String(fileBytes);
            image.setImgEncode(encodedString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String img, String type) {

        String path = PATH_ICONS;
        if (type.equals("news"))
            path = PATH_ICONS;

        byte[] decodedBytes = Base64.decode(img);

        try {
            File imageFile = new File(path);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            outputStream.write(decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void initImages(List<Image> imgs) {
        for (Image image : imgs)
            initImage(image);
    }

    public void initImage(Image img) {

        if (img.getType().equals("icon"))
            img.setPath(PATH_ICONS);
        if (img.getType().equals("news"))
            img.setPath(PATH_NEWS);
    }
}

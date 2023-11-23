package com.example.imageservice.services;

import com.example.imageservice.models.DataImage;
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
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repository;

    @Value("${PATH_ICONS}")
    private String PATH_ICONS;
    @Value("${PATH_NEWS}")
    private String PATH_NEWS;
    @Value("${PATH_MESSAGES}")
    private String PATH_MESSAGES;


    @Transactional
    public void save(DataImage img) {
        repository.save(img);
    }

    @Transactional
    public void saveAll(List<DataImage> imgs) {
        repository.saveAll(imgs);
    }


    /**Есть вариант не использовать путь: по типу и названию можно найти
     * изображение, однако у этого метода есть минус - все изображения должны
     * храниться только в 1 папке */
    public DataImage getImage(String UUID) {
        Optional<DataImage> image_opt = repository.findByUuid(UUID);
        DataImage image = null;

        if (!image_opt.isEmpty()) {
            image = image_opt.get();
            try {
                File file = new File(image.getPath());
                byte[] fileBytes = Files.readAllBytes(file.toPath());

                String encodedString = Base64.toBase64String(fileBytes);
                image.setImage(new Image());
                image.getImage().setImgEncode(encodedString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return image;
    }

    public void saveImage(String img, String type) throws IOException {

        String path = PATH_ICONS;
        if (type.equals("news_image"))
            path = PATH_ICONS;
        if (type.equals("message_image"))
            path = PATH_MESSAGES;

        byte[] decodedBytes = Base64.decode(img);

        File imageFile = new File(path + "/test.png");
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        outputStream.write(decodedBytes);
    }

    public void initImages(List<DataImage> imgs) {
        for (DataImage image : imgs)
            initImage(image);
    }

    public void initImage(DataImage img) {
        if (img.getType().equals("profile_icon"))
            img.setPath(PATH_ICONS);
        if (img.getType().equals("news_image"))
            img.setPath(PATH_NEWS);
        if (img.getType().equals("message_image"))
            img.setPath(PATH_MESSAGES);
    }


}

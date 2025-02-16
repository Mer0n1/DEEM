package com.example.imageservice.services;

import com.example.imageservice.models.*;
import com.example.imageservice.repositories.*;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private NewsImageRepository newsImageRepository;
    @Autowired
    private MessageImageRepository messageImageRepository;
    @Autowired
    private IconImageRepository iconImageRepository;

    @Value("${PATH_ICONS}")
    private String PATH_ICONS;
    @Value("${PATH_NEWS}")
    private String PATH_NEWS;
    @Value("${PATH_MESSAGES}")
    private String PATH_MESSAGES;


    @Transactional
    public void saveIcon(IconImage img) { iconImageRepository.save(img); }
    @Transactional
    public void saveMessageImage(MessageImage img) { messageImageRepository.save(img); }

    @Transactional
    public void saveNewsImage(NewsImage img) { newsImageRepository.save(img); }

    @Transactional
    public void saveAllNewsImages(List<NewsImage> imgs) {
        newsImageRepository.saveAll(imgs);
    }

    @Transactional
    public void saveAllMessageImages(List<MessageImage> imgs) {
        messageImageRepository.saveAll(imgs);
    }

    @Transactional
    public void deleteIcon(int id) { iconImageRepository.deleteById(id);}

    //@Cacheable("iconUuid")
    public IconImage getImageIcon(String uuid) { return iconImageRepository.findByUuid(uuid); }

    public Image getImage(String UUID, String type) throws Exception {
        String path = "";
        Image image = new Image();
        PathImageProjection projection = null;

        if (type.equals("news_image"))
            projection = newsImageRepository.findByUuid(UUID);
        if (type.equals("profile_icon"))
            projection = newProjection(UUID);
        if (type.equals("message_image"))
            projection = messageImageRepository.findByUuid(UUID);

        if (projection != null)
            path = projection.getPath();

        if (!path.isEmpty())
            image.setImgEncode(encodeImage(path));

        return image;
    }

    public List<Image> getImages(String UUID, String type) throws Exception {
        List<Image> images = new ArrayList<>();
        List<PathImageProjection> projections = new ArrayList<>();

        if (type.equals("news_image"))
            projections = newsImageRepository.findAllByUuid(UUID);
        if (type.equals("profile_icon"))
            projections = List.of(newProjection(UUID));
        if (type.equals("message_image"))
            projections = messageImageRepository.findAllByUuid(UUID);

        for (PathImageProjection projection : projections) {
            String path = projection.getPath();
            if (!path.isEmpty()) {
                Image image = new Image();
                image.setImgEncode(encodeImage(path));
                images.add(image);
            }
        }

        return images;
    }

    private PathImageProjection newProjection(String uuid) {
        return new PathImageProjection() {
            @Override
            public String getPath() {
                return iconImageRepository.findByUuid(uuid).getPath();
            }
        };
    }

    public String encodeImage(String path) {
        try {
            File file = new File(path);
            byte[] fileBytes = Files.readAllBytes(file.toPath());

            String encodedString = Base64.toBase64String(fileBytes);
            return encodedString;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImage(String img, String path) throws IOException {

        byte[] decodedBytes = Base64.decode(img);

        File imageFile = new File(path);
        FileOutputStream outputStream = new FileOutputStream(imageFile);
        outputStream.write(decodedBytes);
    }

    public void initNewsImages(List<NewsImage> imgs) {
        NewsImage newsImage = newsImageRepository.findTopByOrderByIdDesc();
        Long CurrentId = 0L;

        if (newsImage != null)
            CurrentId = newsImage.getId();

        for (NewsImage image : imgs)
            image.setPath(getPath("news_image") + ++CurrentId + ".png");
    }

    public void initMessageImages(List<MessageImage> imgs) {
        MessageImage messageImage = messageImageRepository.findTopByOrderByIdDesc();
        Long CurrentId = 0L;

        if (messageImage != null)
            CurrentId = messageImage.getId();

        for (MessageImage image : imgs)
            image.setPath(getPath("message_image") + ++CurrentId + ".png");
    }

    public String getPath(String type) {
        String path = "";
        if (type.equals("profile_icon"))
            path = PATH_ICONS;
        if (type.equals("news_image"))
            path = PATH_NEWS;
        if (type.equals("message_image"))
            path = PATH_MESSAGES;
        return path;
    }

    public int getCountImagesNews(Long idNews) {
        return newsImageRepository.getCount(idNews);
    }
    public int getCountImagesMessage(Long idMessage) {
        return messageImageRepository.getCount(idMessage);
    }
}

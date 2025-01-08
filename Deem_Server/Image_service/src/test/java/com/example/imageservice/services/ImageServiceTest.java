package com.example.imageservice.services;

import com.example.imageservice.models.IconImage;
import com.example.imageservice.models.Image;
import com.example.imageservice.models.MessageImage;
import com.example.imageservice.models.NewsImage;
import com.example.imageservice.repositories.IconImageRepository;
import com.example.imageservice.repositories.MessageImageRepository;
import com.example.imageservice.repositories.NewsImageRepository;
import com.example.imageservice.repositories.PathImageProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Spy
    @InjectMocks
    private ImageService imageService;

    @Mock
    private NewsImageRepository newsImageRepository;
    @Mock
    private MessageImageRepository messageImageRepository;
    @Mock
    private IconImageRepository iconImageRepository;

    private String PATH_ICONS = "icons/";
    private String PATH_NEWS = "news/";
    private String PATH_MESSAGES = "messages/";

    @Test
    void saveIcon() {
        IconImage iconImage = new IconImage();

        when(iconImageRepository.save(iconImage)).thenReturn(iconImage);

        imageService.saveIcon(iconImage);

        verify(iconImageRepository).save(iconImage);
    }

    @Test
    void saveMessageImage() {
        MessageImage messageImage = new MessageImage();

        when(messageImageRepository.save(messageImage)).thenReturn(messageImage);

        imageService.saveMessageImage(messageImage);

        verify(messageImageRepository).save(messageImage);
    }

    @Test
    void saveNewsImage() {
        NewsImage newsImage = new NewsImage();

        when(newsImageRepository.save(newsImage)).thenReturn(newsImage);

        imageService.saveNewsImage(newsImage);

        verify(newsImageRepository).save(newsImage);
    }

    @Test
    void saveAllNewsImages() {
        List<NewsImage> newsImages = Arrays.asList(new NewsImage(), new NewsImage());

        when(newsImageRepository.saveAll(newsImages)).thenReturn(newsImages);

        imageService.saveAllNewsImages(newsImages);

        verify(newsImageRepository).saveAll(newsImages);
    }

    @Test
    void saveAllMessageImages() {
        List<MessageImage> messageImages = Arrays.asList(new MessageImage(), new MessageImage());

        when(messageImageRepository.saveAll(messageImages)).thenReturn(messageImages);

        imageService.saveAllMessageImages(messageImages);

        verify(messageImageRepository).saveAll(messageImages);
    }

    @Test
    void deleteIcon() {
        int id = 1;

        doNothing().when(iconImageRepository).deleteById(any());

        imageService.deleteIcon(id);

        verify(iconImageRepository).deleteById(any());
    }

    @Test
    void getImageIcon() {
        String uuid = "111";
        IconImage iconImage = new IconImage();
        when(iconImageRepository.findByUuid(uuid)).thenReturn(iconImage);

        IconImage result = imageService.getImageIcon(uuid);

        verify(iconImageRepository).findByUuid(uuid);
        assertEquals(iconImage, result);
    }

    /*@Test
    void getImageTest() throws Exception {
        String uuid = "111";
        String type = "news_image";

        PathImageProjection projection = new PathImageProjection() {
            @Override
            public String getPath() {
                return "some/path/to/image.png";
            }
        };

        when(newsImageRepository.findByUuid(uuid)).thenReturn(projection);
        when(imageService.encodeImage(any())).thenReturn(projection.getPath());

        Image result = imageService.getImage(uuid, type);

        verify(newsImageRepository).findByUuid(uuid);
        assertNotNull(result.getImgEncode());
        assertEquals(result.getImgEncode(), projection.getPath());
    }*/

    @Test
    void initNewsImages() {
        List<NewsImage> newsImages = Arrays.asList(new NewsImage(), new NewsImage());
        NewsImage lastSavedImage = new NewsImage();
        lastSavedImage.setId(10L);

        when(newsImageRepository.findTopByOrderByIdDesc()).thenReturn(lastSavedImage);

        imageService.initNewsImages(newsImages);

        assertEquals("null11.png", newsImages.get(0).getPath());
        assertEquals("null12.png", newsImages.get(1).getPath());
    }

    @Test
    void initMessageImages() {
        List<MessageImage> messageImages = Arrays.asList(new MessageImage(), new MessageImage());
        MessageImage lastSavedImage = new MessageImage();
        lastSavedImage.setId(5L);

        when(messageImageRepository.findTopByOrderByIdDesc()).thenReturn(lastSavedImage);

        imageService.initMessageImages(messageImages);

        assertEquals("null6.png", messageImages.get(0).getPath());
        assertEquals("null7.png", messageImages.get(1).getPath());
    }

    @Test
    void getCountImagesNews() {
        Long idNews = 1L;
        int count = 10;
        when(newsImageRepository.getCount(idNews)).thenReturn(count);

        int result = imageService.getCountImagesNews(idNews);

        verify(newsImageRepository).getCount(idNews);
        assertEquals(count, result);
    }

    @Test
    void getCountImagesMessage() {
        Long idMessage = 1L;
        int count = 10;
        when(messageImageRepository.getCount(idMessage)).thenReturn(count);

        int result = imageService.getCountImagesMessage(idMessage);

        verify(messageImageRepository).getCount(idMessage);
        assertEquals(count, result);
    }

    /*@Test
    void getImage() { //TODO
        String  UUID = "11",
                news_image_type = "news_image",
                profile_icon_type    = "profile_icon",
                message_image_type   = "message_image";
        PathImageProjection projection = mock(PathImageProjection.class);

        when(newsImageRepository.findByUuid(UUID)).thenReturn(projection);
        when(messageImageRepository.findByUuid(UUID)).thenReturn(projection);
        when(projection.getPath()).thenReturn(PATH_ICONS);

        Image news_image    = imageService.getImage(UUID, news_image_type);
        Image profile_icon  = imageService.getImage(UUID, profile_icon_type);
        Image message_image = imageService.getImage(UUID, message_image_type);

        verify(newsImageRepository).findByUuid(UUID);
        verify(messageImageRepository).findByUuid(UUID);

        assertNotNull(news_image);
        assertNotNull(profile_icon);
        assertNotNull(message_image);
    }*/



}
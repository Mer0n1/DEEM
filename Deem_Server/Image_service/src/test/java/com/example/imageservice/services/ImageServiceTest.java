package com.example.imageservice.services;

import com.example.imageservice.models.Image;
import com.example.imageservice.repositories.IconImageRepository;
import com.example.imageservice.repositories.MessageImageRepository;
import com.example.imageservice.repositories.NewsImageRepository;
import com.example.imageservice.repositories.PathImageProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private NewsImageRepository newsImageRepository;
    @Mock
    private MessageImageRepository messageImageRepository;
    @Mock
    private IconImageRepository iconImageRepository;

    @Value("${PATH_ICONS}")
    private String PATH_ICONS;

    @Test
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
    }

    @Test
    void saveImage() {
    }

    @Test
    void initNewsImages() {
    }

    @Test
    void initMessageImages() {
    }

    @Test
    void getPath() {
    }

}
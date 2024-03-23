package com.example.imageservice.controllers;

import com.example.imageservice.models.IconImage;
import com.example.imageservice.models.Image;
import com.example.imageservice.models.MessageImage;
import com.example.imageservice.models.NewsImage;
import com.example.imageservice.services.ImageService;
import com.example.imageservice.util.Validator;
import org.aspectj.bridge.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BindingResult;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @InjectMocks
    private ImageController imageController;

    @Mock
    private ImageService imageService;

    @Mock
    private Validator validator;

    @Test
    void getCount() {
        Long id = 1L;

        when(imageService.getCountImagesNews(id)).thenReturn(1);
        when(imageService.getCountImagesMessage(id)).thenReturn(2);

        Integer news_image    = imageController.getCount("news_image", id);
        Integer message_image = imageController.getCount("message_image", id);

        verify(imageService).getCountImagesMessage(id);
        verify(imageService).getCountImagesNews(id);

        assertEquals(1, news_image);
        assertEquals(2, message_image);
    }

    @Test
    void getImage() {
        String UUID = "111";
        String type = "type";

        when(imageService.getImage(UUID, type)).thenReturn(new Image());

        Image image = imageController.getImage(UUID, type);

        verify(imageService).getImage(UUID, type);

        assertNotNull(image);
    }

    @Test
    void addImageIcon() throws IOException {
        BindingResult bindingResult = mock(BindingResult.class);
        Principal principal = mock(Principal.class);
        IconImage image = new IconImage();
        image.setUuid("1");
        image.setImage(new Image());

        when(principal.getName()).thenReturn("AnyName");
        when(imageService.getPath("profile_icon")).thenReturn("somePath");
        when(imageService.getImageIcon(image.getUuid())).thenReturn(null);

        imageController.addImageIcon(image, bindingResult, principal);

        verify(imageService).getPath(any());
        verify(imageService).getImageIcon(any());
        verify(imageService).saveIcon(image);
    }

    @Test
    void addImagesNews() throws IOException {
        NewsImage newsImage = new NewsImage();
        newsImage.setImage(new Image());
        List<NewsImage> imgs = List.of(newsImage);

        when(validator.hasErrorsNewsImages(imgs)).thenReturn(false);

        imageController.addImagesNews(imgs);

        verify(validator).hasErrorsNewsImages(imgs);
        verify(imageService).initNewsImages(imgs);
        verify(imageService).saveImage(any(), any());
        verify(imageService).saveAllNewsImages(imgs);
    }

    @Test
    void addImagesMessage() throws IOException {
        MessageImage messageImage = new MessageImage();
        messageImage.setImage(new Image());
        List<MessageImage> imgs = List.of(messageImage);

        when(validator.hasErrorsMessageImages(imgs)).thenReturn(false);

        imageController.addImagesMessage(imgs);

        verify(validator).hasErrorsMessageImages(imgs);
        verify(imageService).initMessageImages(imgs);
        verify(imageService).saveImage(any(), any());
        verify(imageService).saveAllMessageImages(imgs);
    }
}
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        String news_image_type = "news_image";
        String message_image_type = "message_image";
        int result_news = 1;
        int result_message = 2;

        when(imageService.getCountImagesNews(id)).thenReturn(result_news);
        when(imageService.getCountImagesMessage(id)).thenReturn(result_message);

        Integer news_image    = imageController.getCount(news_image_type, id);
        Integer message_image = imageController.getCount(message_image_type, id);

        verify(imageService).getCountImagesMessage(id);
        verify(imageService).getCountImagesNews(id);

        assertEquals(result_news, news_image);
        assertEquals(result_message, message_image);
    }

    @Test
    void getImage() throws Exception {
        String UUID = "111";
        String type = "type";

        when(imageService.getImage(UUID, type)).thenReturn(new Image());

        Image image = imageController.getImage(UUID, type);

        verify(imageService).getImage(UUID, type);
        assertNotNull(image);
    }

    @Test
    void getImageException() throws Exception {
        String UUID = "111";
        String type = "type";

        when(imageService.getImage(any(), any())).thenThrow(new Exception());

        Image image = imageController.getImage(UUID, type);

        verify(imageService).getImage(any(), any());
        assertNull(image);
    }

    @Test
    void addImageIcon() throws IOException {
        BindingResult bindingResult = mock(BindingResult.class);
        String name = "Name";
        String path = "path/";
        Principal principal = ()->name;
        IconImage image = new IconImage();
        image.setUuid("1");
        image.setImage(new Image());

        when(imageService.getPath("profile_icon")).thenReturn(path);
        when(imageService.getImageIcon(image.getUuid())).thenReturn(null);
        doNothing().when(imageService).saveIcon(image);
        doNothing().when(imageService).saveImage(any(), any());

        ResponseEntity<?> response = imageController.addImageIcon(image, bindingResult, principal);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(image.getPath(), path + name + ".png");

        verify(imageService).getPath(any());
        verify(imageService).getImageIcon(image.getUuid());
        verify(imageService).saveIcon(image);
        verify(imageService).saveImage(any(), any());
    }

    @Test
    void addImagesNews() throws IOException {
        NewsImage newsImage = new NewsImage();
        newsImage.setImage(new Image());
        List<NewsImage> imgs = List.of(newsImage);

        doNothing().when(imageService).initNewsImages(imgs);
        doNothing().when(imageService).saveImage(any(), any());
        doNothing().when(imageService).saveAllNewsImages(imgs);

        ResponseEntity<?> response = imageController.addImagesNews(imgs);

        verify(imageService).initNewsImages(imgs);
        verify(imageService).saveImage(any(), any());
        verify(imageService).saveAllNewsImages(imgs);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void addImagesMessage() throws IOException {
        MessageImage messageImage = new MessageImage();
        messageImage.setImage(new Image());
        List<MessageImage> imgs = List.of(messageImage);

        doNothing().when(imageService).initMessageImages(imgs);
        doNothing().when(imageService).saveImage(any(), any());
        doNothing().when(imageService).saveAllMessageImages(imgs);

        ResponseEntity<?> response = imageController.addImagesMessage(imgs);

        verify(imageService).initMessageImages(imgs);
        verify(imageService).saveImage(any(), any());
        verify(imageService).saveAllMessageImages(imgs);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
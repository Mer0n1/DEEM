package com.example.restful.utils;

import androidx.lifecycle.MutableLiveData;

import com.example.restful.api.APIManager;
import com.example.restful.models.CreateMessageDTO;
import com.example.restful.models.CreateNewsDTO;
import com.example.restful.models.Message;
import com.example.restful.models.News;
import com.example.restful.models.ReceiveMessageDto;

public class ConverterDTO {

    private ConverterDTO() {}

    public static CreateMessageDTO MessageToCreateMessageDTO(Message message) {
        CreateMessageDTO dto = new CreateMessageDTO();
        dto.setChat(message.getChat());
        dto.setAuthor(message.getAuthor());
        dto.setDate(message.getDate());
        dto.setText(message.getText());
        dto.setImages(message.getImages().getValue());
        dto.getChat().setMessages(message.getChat().getMessages());
        dto.setThereVideo(message.getThereVideo());
        dto.setVideoUUID(message.getVideoUUID());
        return dto;
    }

    public static Message CreateMessageDTOToMessage(CreateMessageDTO dto) {
        Message message = new Message();
        message.setChat(dto.getChat());
        message.setDate(dto.getDate());
        message.setAuthor(dto.getAuthor());
        message.setText(dto.getText());
        //message.setId(dto.getId());
        message.setChatId((long) dto.getChat().getId());
        message.setImages(new MutableLiveData<>());
        message.setVideoUUID(dto.getVideoUUID());
        return message;
    }

    public static Message CreateMessageDTOToMessage(ReceiveMessageDto dto) {
        Message message = new Message();
        message.setChat(dto.getChat());
        message.setDate(dto.getDate());
        message.setAuthor(dto.getAuthor());
        message.setText(dto.getText());
        message.setId(Long.valueOf(dto.getId()));
        message.setChatId((long) dto.getChat().getId());
        message.setImages(new MutableLiveData<>());
        message.setVideoUUID(dto.getVideoUUID());
        return message;
    }

    public static News CreateNewsDTOToNews(CreateNewsDTO dto) {
        News news = new News();
        news.setContent(dto.getContent());
        news.setIdAuthor(dto.getIdAuthor());
        news.setFaculty(dto.getFaculty());
        news.setDate(dto.getDate());
        news.setId(dto.getId());
        news.setImages(new MutableLiveData<>());
        news.getImages().setValue(dto.getImages());
        news.setGroup(APIManager.getManager().getListGroups().stream().filter
                (x->x.getId().equals(news.getIdGroup())).findAny().orElse(null));

        return news;
    }

    public static CreateNewsDTO NewsToCreateNewsDTO(News news) {
        CreateNewsDTO dto = new CreateNewsDTO();
        dto.setIdAuthor(news.getIdAuthor());
        dto.setContent(news.getContent());
        dto.setFaculty(news.getFaculty());
        dto.setId(news.getId());
        dto.setDate(news.getDate());
        dto.setIdGroup(news.getIdGroup());
        dto.setImages(news.getImages().getValue());

        return dto;
    }
}

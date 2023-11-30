package com.example.imageservice.util;

import com.example.imageservice.models.Image;
import com.example.imageservice.models.MessageImage;
import com.example.imageservice.models.NewsImage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Validator {

    public boolean hasErrorsMessageImages(List<MessageImage> objs) {
        if (objs == null)
            return true;

        for (MessageImage image : objs) {
            if (image.getId_message() == null || image.getId_message() == 0)
                return true;
            if (hasErrorsUUIDAndImage(image.getUuid(), image.getImage()))
                return true;
        }

        return false;
    }

    public boolean hasErrorsNewsImages(List<NewsImage> objs) {
        if (objs == null)
            return true;

        for (NewsImage image : objs) {
            if (image.getId_news() == null || image.getId_news() == 0)
                return true;
            if (hasErrorsUUIDAndImage(image.getUuid(), image.getImage()))
                return true;
        }

        return false;
    }

    private boolean hasErrorsUUIDAndImage(String uuid, Image image) {
        if (uuid == null || uuid.isEmpty())
            return true;

        if (image == null || image.getImgEncode() == null ||
                image.getImgEncode().isEmpty())
            return true;

        return false;
    }
}

package com.example.news_service.services;

import com.example.news_service.models.News;
import com.example.news_service.models.images.NewsImage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestTemplateService {
    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("http://localhost:8086/image")
    private String imageServiceUrl;
    @Value("http://localhost:8087/push/sendNews")
    private String pushServiceUrl;
    @Value("http://localhost:8093/video")
    private String videoServiceUrl;

    private String personal_key;

    private MultiValueMap<String, String> headers;
    private HttpEntity<String> entity;

    RestTemplateService(RestTemplate restTemplate, Environment environment) {
        headers = new LinkedMultiValueMap<>();
        personal_key = environment.getProperty("ADMIN_KEY");
        headers.add("Authorization", "Bearer " + personal_key);
        headers.set("Content-Type", "application/json");

        entity = new HttpEntity<>("", headers);
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public ResponseEntity<?> addImagesNews(List<NewsImage> imgs) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(imgs);
        entity = new HttpEntity<>(jsonMessage, headers);

        return restTemplate.exchange(imageServiceUrl + "/addImagesNews", HttpMethod.POST, entity, Void.class);
    }

    public void pushNewsTo(News news) throws JsonProcessingException {
        String jsonMessage = (new ObjectMapper().writer().withDefaultPrettyPrinter())
                .writeValueAsString(news);

        entity = new HttpEntity<>(jsonMessage, headers);

        restTemplate.exchange(pushServiceUrl, HttpMethod.POST, entity, Void.class);
    }

    public ResponseEntity<String> getVideoUUID(Long idMessage) {
        return restTemplate.exchange(videoServiceUrl + "/getUUID" + "?type=news_video&id="
                + idMessage, HttpMethod.GET, entity, String.class);
    }
}

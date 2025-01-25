package com.example.video_service.controllers;

import com.example.video_service.models.VideoMetadata;
import com.example.video_service.services.VideoBaseDataService;
import com.example.video_service.services.VideoService;
import com.example.video_service.utils.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


/**
 * В прошлом с ImageService мы действовали по другому: получали например news
 * на стороне клиента, затем спрашивали у ImageService есть ли изображения и если
 * были то запрашивали их.
 *
 * С видео мы используем более современный подход. Мы будем генерировать UUID
 * на основе хэша видео, чтобы не зависеть от дом данных, затем NewsService
 * перед возвращением новости будет запрашивать UUID через getUUIDbyContentId
 * а уже клиент зная uuid будет запрашивать видео.
 * В этом новом подходе мы меняем систему генерации UUID, а также систему получения
 * данных через более простой способ.
 * */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;
    @Autowired
    private VideoBaseDataService videoBaseDataService;
    @Autowired
    private Validator validator;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file,
                                         @RequestParam("metadata") String metadataJson) {
        System.out.println("get upload");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            VideoMetadata video = objectMapper.readValue(metadataJson, VideoMetadata.class);
            validator.validate(video);
            //проверить существует ли подобный id? TODO

System.out.println("upload: " + video.toString());
            String filePath = videoService.uploadVideo(file, video);
            videoBaseDataService.save(video, filePath);
            return ResponseEntity.ok("Видео успешно загружено и подготовлено для DASH");
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getManifest/{uuid}")
    public ResponseEntity<String> getVideoManifest(@PathVariable String uuid) {
        System.out.println("getVideoManifest " + uuid);
        try {
            return ResponseEntity.ok(videoService.getManifestUrl(uuid).replace("/", "%2F"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /** Метод созданный для получения UUID.
     * Аккаунт может запросить UUID, но только через сервисы новостей и сообщений,
     * которые в свою очередь проверят валидность запроса на своей стороне и запросят
     * UUID через restTemplate. */
    @PreAuthorize("hasRole('HIGH')")
    @GetMapping("/getUUID")
    public ResponseEntity<String> getUUIDbyContentId(@RequestParam("type") String type,
                                                     @RequestParam("id") Long id) {
        try {
            String uuid = "";
            if (type.equals("message_video"))
                uuid = videoBaseDataService.getUUIDbyMessageId(id);
            else if (type.equals("news_video"))
                uuid = videoBaseDataService.getUUIDbyNewsId(id);

            if (uuid.isEmpty())
                throw new Exception("Id не найден.");

            return ResponseEntity.ok(uuid);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }
}

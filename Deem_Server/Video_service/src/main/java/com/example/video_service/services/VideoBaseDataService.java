package com.example.video_service.services;

import com.example.video_service.models.VideoMessage;
import com.example.video_service.models.VideoMetadata;
import com.example.video_service.models.VideoNews;
import com.example.video_service.repositories.VideoMessageRepository;
import com.example.video_service.repositories.VideoNewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoBaseDataService {

    @Autowired
    private VideoMessageRepository videoMessageRepository;
    @Autowired
    private VideoNewsRepository videoNewsRepository;

    public void save(VideoNews videoNews) { videoNewsRepository.save(videoNews); }

    public void save(VideoMessage videoMessage) { videoMessageRepository.save(videoMessage); }

    public void save(VideoMetadata videoMetadata, String currentPath) {

        if (videoMetadata.getType().equals("news_video")) {
            VideoNews videoNews = new VideoNews();
            videoNews.setId_news(videoMetadata.getId_dependency());
            videoNews.setUuid(videoMetadata.getUuid());
            videoNews.setPath(currentPath);
            save(videoNews);

        } else
            if (videoMetadata.getType().equals("message_video")) {
                VideoMessage videoMessage = new VideoMessage();
                videoMessage.setId_message(videoMetadata.getId_dependency());
                videoMessage.setUuid(videoMetadata.getUuid());
                videoMessage.setPath(currentPath);
                save(videoMessage);
            }
    }

    public String getUUIDbyNewsId(Long id) {
        return videoNewsRepository.findUUIDById(id);
    }

    public String getUUIDbyMessageId(Long id) {
        return videoMessageRepository.findUUIDById(id);
    }
}

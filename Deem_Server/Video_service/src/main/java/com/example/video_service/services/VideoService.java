package com.example.video_service.services;

import com.example.video_service.models.VideoMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class VideoService {

    @Value("${PATH_VIDEOS}")
    private String PATH_VIDEOS;
    @Value("${MANIFEST_PATH}")
    private String MANIFEST_PATH;

    /** Загружает видео согласно name и uuid и возвращает путь к файлу */
    public String uploadVideo(MultipartFile file, VideoMetadata video) throws IOException {
        String filePath = saveFile(file, video.getUuid());
        convertToHLS(filePath, PATH_VIDEOS + "/" + video.getUuid() + "/");
        return filePath;
    }

    public void convertToDash(String inputPath, String outputDir) throws IOException {
        String command = String.format(
                "ffmpeg -i %s -map 0 -seg_duration 4 -f dash %s/output.mpd",
                inputPath, outputDir
        );

        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public void convertToHLS(String inputPath, String outputDir) throws IOException {
        String command = String.format(
                "ffmpeg -i \"%s\" -codec: copy -start_number 0 -hls_time 10 -hls_list_size 0 -f hls \"%s/output.m3u8\"",
                inputPath, outputDir
        );

        ProcessBuilder pb = new ProcessBuilder(command.split(" "));
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    public String saveFile(MultipartFile file, String uuid) throws IOException {
        // Указываем путь, куда будет сохранен файл
        //String uploadDir = "D:/Temp/videos/";
        String uploadDir = PATH_VIDEOS + "/" + uuid + "/";

        // Создаем папку, если ее еще нет
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Генерируем имя файла
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Сохраняем файл
        File destinationFile = new File(uploadDir + fileName);
        file.transferTo(destinationFile);

        // Возвращаем путь сохраненного файла
        return destinationFile.getAbsolutePath();
    }

    public String getManifestUrl(String uuid) throws Exception {
        String pathDir = PATH_VIDEOS + "/" + uuid;

        File dir = new File(pathDir);
        if (!dir.exists())
            throw new Exception("Некорректный запрос.");
        else
            return MANIFEST_PATH + "/" + uuid + "/output.m3u8";
    }
}

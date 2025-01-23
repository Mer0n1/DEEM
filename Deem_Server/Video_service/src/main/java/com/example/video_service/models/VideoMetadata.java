package com.example.video_service.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

/**
 * Общий обьект для конвертирования Message/News
 * */
@Data
@ToString
public class VideoMetadata {
    @NotNull
    private Long id_dependency;
    @NotEmpty
    private String type;
    @NotEmpty
    private String uuid;
}

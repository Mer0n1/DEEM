package com.example.exam_service.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class EventPush {
    public enum Extension { person, group, course, faculty, university }

    @NotEmpty
    private Event event;
    @NotNull
    private Extension type;
}

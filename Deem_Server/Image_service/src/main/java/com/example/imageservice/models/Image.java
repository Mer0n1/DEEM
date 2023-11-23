package com.example.imageservice.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Image {
    @NotEmpty
    private String imgEncode;
}

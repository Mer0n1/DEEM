package com.example.exam_taller_service.models;

import lombok.Data;

import java.util.List;

@Data
public class MemberRequest {
    private List<Long> members;
}

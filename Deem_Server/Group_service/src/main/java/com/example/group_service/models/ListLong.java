package com.example.group_service.models;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListLong {
    public List<Long> list;

    public ListLong() {
        list = new ArrayList<>();
    }
}

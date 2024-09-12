package com.example.restful.datebase.converters;

import androidx.room.TypeConverter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterListLong {
    @TypeConverter
    public String fromLongList(List<Long> longList) {
        if (longList == null) {
            return null;
        }
        return longList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @TypeConverter
    public List<Long> toLongList(String longString) {
        if (longString == null || longString.isEmpty()) {
            return null;
        }
        return Arrays.stream(longString.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}

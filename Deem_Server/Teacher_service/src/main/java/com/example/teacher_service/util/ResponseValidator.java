package com.example.teacher_service.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ResponseValidator {

    public static Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors())
            errorMap.put(error.getField(), error.getDefaultMessage());
        return errorMap;
    }
}

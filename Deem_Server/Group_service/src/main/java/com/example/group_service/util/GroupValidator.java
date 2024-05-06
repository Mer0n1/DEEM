package com.example.group_service.util;

import com.example.group_service.models.Group;
import com.example.group_service.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GroupValidator implements Validator {
    @Autowired
    private GroupRepository groupRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Group.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Group group = (Group) target;

        if (!groupRepository.findByName(group.getName()).isEmpty())
            errors.rejectValue("name", "", "Такое название уже существует");
    }
}

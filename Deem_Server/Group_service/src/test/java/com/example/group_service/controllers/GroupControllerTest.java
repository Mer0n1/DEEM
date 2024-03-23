package com.example.group_service.controllers;

import com.example.group_service.models.Group;
import com.example.group_service.models.LocationStudent;
import com.example.group_service.services.GroupService;
import com.example.group_service.services.RestTemplateClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class GroupControllerTest {

    @InjectMocks
    protected GroupController groupController;

    @Mock
    private GroupService groupService;

    @Mock
    private RestTemplateClient restTemplateClient;

    @Test
    void getGroup() {
        Long id = 1L;
        Group group = new Group();
        group.setId(1L);

        when(groupService.getGroup(id)).thenReturn(group);
        when(restTemplateClient.getListIdUsersOfGroup(group.getId())).thenReturn(new ArrayList<>());
        when(restTemplateClient.getListTopGroups(anyList())).thenReturn(new ArrayList<>());

        Group TestGroup = groupController.getGroup(id);

        verify(groupService).getGroup(id);
        verify(restTemplateClient).getListIdUsersOfGroup(anyLong());
        verify(restTemplateClient).getListTopGroups(anyList());

        assertEquals(group, TestGroup);
    }

    @Test
    void getGroupsOfFaculty() { //TODO
        Authentication authentication = mock(Authentication.class);

    }

    @Test
    void getAllGroups() { //TODO
        Group group = new Group();
        group.setId(1L);
        Group group2 = new Group();
        group.setId(2L);
        List<Group> list = List.of(group, group2);

        when(groupService.getGroups()).thenReturn(list);
        when(restTemplateClient.getListIdUsersOfGroup(1L)).thenReturn(List.of(1L,2L));
        when(restTemplateClient.getListTopGroups(anyList())).thenReturn(List.of(1L,2L));

        List<Group> groupList = groupController.getAllGroups();

        verify(groupService).getGroups();
        verify(restTemplateClient).getListIdUsersOfGroup(1L);
        verify(restTemplateClient).getListTopGroups(anyList());

        assertNotNull(groupList);
    }

    @Test
    void getLocationStudent() {
        Group group = new Group();
        group.setId(1L);
        group.setCourse(1);
        group.setFaculty("EPF");

        when(groupService.getGroup(group.getId())).thenReturn(group);

        LocationStudent locationStudent = groupController.getLocationStudent(group.getId());

        verify(groupService).getGroup(any());

        assertNotNull(locationStudent);
    }

    @Test
    void createGroup() {
        Group group = new Group();
        BindingResult bindingResult = mock(BindingResult.class);

        ResponseEntity<?> responseEntity = groupController.createGroup(group, bindingResult);

        verify(groupService).save(group);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getChatId() {
        Long id = 1L;
        Group group = new Group();
        group.setChat_id(1L);

        when(groupService.getGroup(id)).thenReturn(group);

        Long TestId = groupController.getChatId(id);

        verify(groupService).getGroup(any());

        assertEquals(1L, TestId);
    }
}
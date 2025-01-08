package com.example.group_service.controllers;

import com.example.group_service.config.PersonDetails;
import com.example.group_service.models.Group;
import com.example.group_service.models.ListLong;
import com.example.group_service.models.LocationStudent;
import com.example.group_service.services.GroupService;
import com.example.group_service.services.RestTemplateClient;
import com.example.group_service.util.GroupValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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

    @Spy
    @InjectMocks
    protected GroupController groupController;

    @Mock
    private GroupService groupService;

    @Mock
    private RestTemplateClient restTemplateClient;

    @Mock
    private GroupValidator groupValidator;

    @Test
    void getGroup() {
        Long id = 1L;
        Group group = new Group();
        group.setId(id);

        doNothing().when(groupController).buildListGroups(anyList());
        when(groupService.getGroup(id)).thenReturn(group);

        Group testGroup = groupController.getGroup(id);

        verify(groupService).getGroup(id);
        verify(groupController).buildListGroups(anyList());
        assertEquals(group, testGroup);
    }

    @Test
    void getGroupsOfFaculty() {
        PersonDetails personDetails = new PersonDetails();
        personDetails.setFaculty("EPF");
        personDetails.setCourse(1);

        doNothing().when(groupController).buildListGroups(anyList());
        when(groupService.getGroupsOfFacultyAndCourse(personDetails.getFaculty(), personDetails.getCourse()))
                .thenReturn(anyList());

        List<Group> groups = groupController.getGroupsOfFaculty(personDetails);

        verify(groupService).getGroupsOfFacultyAndCourse(personDetails.getFaculty(), personDetails.getCourse());
        assertNotNull(groups);
    }

    @Test
    void getAllGroups() {

        doNothing().when(groupController).buildListGroups(anyList());
        when(groupService.getGroups()).thenReturn(new ArrayList<>());

        List<Group> groups = groupController.getAllGroups();

        verify(groupService).getGroups();
        assertNotNull(groups);
    }

    @Test
    void getLocationStudent() {
        Long idGroup = 1L;

        Group group = new Group();
        group.setId(idGroup);
        group.setCourse(1);
        group.setFaculty("EPF");

        when(groupService.getGroup(idGroup)).thenReturn(group);

        LocationStudent locationStudent = groupController.getLocationStudent(idGroup);

        verify(groupService).getGroup(any());
        assertNotNull(locationStudent);
        assertEquals(locationStudent.getFaculty(), group.getFaculty());
        assertEquals(locationStudent.getCourse(), group.getCourse());
    }

    @Test
    void createGroup() {
        Group group = new Group();
        BindingResult bindingResult = mock(BindingResult.class);

        doNothing().when(groupValidator).validate(group, bindingResult);

        ResponseEntity<?> responseEntity = groupController.createGroup(group, bindingResult);

        verify(groupService).save(group);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void getChatId() {
        Long id = 1L;
        Group group = new Group();
        group.setId(id);
        group.setChat_id(2L);

        when(groupService.getGroup(id)).thenReturn(group);

        Long TestId = groupController.getChatId(id);

        assertEquals(group.getChat_id(), TestId);
        verify(groupService).getGroup(group.getId());
    }

    @Test
    void deleteGroup() {
        Integer id_group = 1;

        doNothing().when(groupService).deleteGroup(id_group);

        ResponseEntity<?> responseEntity = groupController.deleteGroup(id_group);

        verify(groupService).deleteGroup(id_group);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /*@Test
    void buildListGroups() {
        Group group = new Group();
        group.setId(1L);
        Group group2 = new Group();
        group.setId(2L);
        List<Group> list = List.of(group, group2);

        when(restTemplateClient.getListIdUsersOfGroup(group.getId())).thenReturn(List.of(3L,2L));
        when(restTemplateClient.getListIdUsersOfGroup(group2.getId())).thenReturn(List.of(1L,4L));
        when(restTemplateClient.getListTopGroups(anyList())).thenReturn(List.of(11L, 12L));

        groupController.buildListGroups(list);

        verify(restTemplateClient).getListIdUsersOfGroup(group.getId());
        verify(restTemplateClient).getListIdUsersOfGroup(group2.getId());
        verify(restTemplateClient).getListTopGroups(anyList());
        assertEquals(11, group.getScore());
        assertEquals(12, group2.getScore());
        assertEquals(List.of(3L, 2L), group.getUsers());
        assertEquals(List.of(1L, 4L), group2.getUsers());
    }*/

}
package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PrivateAccountDTO {
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String fathername;
    private Long group_id;
    private Group group;
    private String role;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getFathername() {
        return fathername;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public Group getGroup() {
        return group;
    }

    public String getRole() {
        return role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

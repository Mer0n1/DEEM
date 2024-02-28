package com.example.restful.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Club {

    private String name;
    private String description;
    private Long id_group;
    private Long id_leader;

    private Group group;
    private Account account;
    private Image icon;

    public String getName() {
        return name;
    }

    public Group getGroup() {
        return group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId_group() {
        return id_group;
    }

    public void setId_group(Long id_group) {
        this.id_group = id_group;
    }

    public Long getId_leader() {
        return id_leader;
    }

    public Account getAccount() {
        return account;
    }

    public void setId_leader(Long id_leader) {
        this.id_leader = id_leader;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}

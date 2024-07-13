package com.example.exam_taller_service.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "list_exam_tallers")
public class List_element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_account")
    private Long id_account;

    public List_element(Long id_account) {
        this.id_account = id_account;
    }

    public List_element() {
    }
}

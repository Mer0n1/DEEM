package com.example.administrative_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "transfer_form")
@Data
public class TransferForm {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "idStudent")
    private Long idStudent;

    //группа в которую нужно перевести. Обязательно текущий факультет.
    @Column(name = "groupTo")
    private String groupTo;
}

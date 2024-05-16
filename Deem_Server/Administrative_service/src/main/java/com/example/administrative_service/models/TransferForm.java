package com.example.administrative_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "transfer_form")
@Data
public class TransferForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "id_student")
    private Long idStudent;

    //группа в которую нужно перевести. Обязательно текущий факультет.
    @NotNull
    @Column(name = "id_group")
    private Long id_group;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Date")
    private Date date;
}

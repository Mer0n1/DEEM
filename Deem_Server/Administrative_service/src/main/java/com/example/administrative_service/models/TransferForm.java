package com.example.administrative_service.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "transfer_form")
@Data
public class TransferForm {

    @Id
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "idStudent")
    private Long idStudent;

    //группа в которую нужно перевести. Обязательно текущий факультет.
    @NotNull
    @Column(name = "id_group")
    private Long id_group;
}

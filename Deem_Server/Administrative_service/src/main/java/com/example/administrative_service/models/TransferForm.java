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

    @NotEmpty(message = "Description must not be null")
    @Column(name = "description")
    private String description;

    @NotNull(message = "Id Student must not be null")
    @Column(name = "idStudent")
    private Long idStudent;

    //группа в которую нужно перевести. Обязательно текущий факультет.
    @NotNull(message = "id_group must not be null")
    @Column(name = "id_group")
    private Long id_group;
}

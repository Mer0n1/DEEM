package com.example.administrative_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
//@Table
@Data
public class TransferForm {

    @Id
    private Long id;

    private String description;

    private Long idStudent;

    //группа в которую нужно перевести. Обязательно текущий факультет.
    private String groupTo;
}

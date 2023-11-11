package com.example.administrative_service.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
//@Table("")
@Data
public class ExclusionForm {

    @Id
    private Long id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Long idStudent;
}

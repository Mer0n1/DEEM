package com.example.messenger_service.models;

import com.example.messenger_service.models.Image.MessageImage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "message")
@Data
@ToString
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "content")
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Date")
    private Date date;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_chat", referencedColumnName = "id")
    @JsonIgnoreProperties("messages")
    private Chat chat;

    @NotNull
    @Column(name = "id_account")
    private Long author;

    @NotNull
    @Column(name = "video")
    private Boolean isThereVideo;

    @Transient
    private List<MessageImage> images;

    @Transient
    private String VideoUUID;
}

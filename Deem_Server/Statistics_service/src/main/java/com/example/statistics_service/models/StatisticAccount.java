package com.example.statistics_service.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "statistic_score_history")
public class StatisticAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_account")
    private Long idAccount;

    @Column(name = "ScoreThisMonth")
    private int ScoreThisMonth;

    @Column(name = "date")
    private Date date;

    @Transient
    private List<SubmissionForm> forms;


    public StatisticAccount(Long idAccount) {
        this.idAccount = idAccount;
        forms = new ArrayList<>();
    }

    public void addScore(int score) {
        ScoreThisMonth += score;
    }
}

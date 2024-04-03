package com.jedi.isolationlevel.isolation;

import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class GameTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer score;

    private Integer credit;

    public static GameTask create(String name, int score) {
        GameTask gameTask = new GameTask();
        gameTask.setName(name);
        gameTask.setScore(score);
        gameTask.setCredit(0);
        return gameTask;
    }

}

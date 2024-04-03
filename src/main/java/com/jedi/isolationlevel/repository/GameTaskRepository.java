package com.jedi.isolationlevel.repository;

import com.jedi.isolationlevel.isolation.GameTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameTaskRepository extends JpaRepository<GameTask, Long> {

    @Modifying
    @Query(value = "truncate table game_task", nativeQuery = true)
    void truncate();

    @Modifying
    @Query(value = "DELETE FROM sqlite_sequence WHERE name = 'game_task';", nativeQuery = true)
    void vacuum();

    @Modifying
    @Query(value = "UPDATE game_task SET credit = credit + 1 WHERE score>=:score",
            nativeQuery = true)
    void updateCreditGreaterThan(@Param("score") Integer score);

    List<GameTask> findGameTasksByScoreGreaterThan(Integer score);
}

package com.appscrip.trivia.datamodel.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.appscrip.trivia.datamodel.local.entity.GameEntity;
import com.appscrip.trivia.datamodel.local.entity.HistoryEntity;
import com.appscrip.trivia.datamodel.local.entity.QuestionEntity;
import com.appscrip.trivia.datamodel.local.entity.SelectedAnswerEntity;

import java.util.List;

@Dao
public interface AppDao {
    //--------------Add Game------------------
    @Insert
    long addGame(GameEntity gameEntity);

    @Query("Select * from " + AppDataBase.TABLE.GAME_TABLE + " Where " + GameEntity.GAME_ID + "= :gameID")
    GameEntity getGame(long gameID);

    @Query("Select MAX (" + GameEntity.GAME_ID + ")" + " From " + AppDataBase.TABLE.GAME_TABLE)
    long getCurrentGameLevelNumber();

    //--------------Question-------------------
    @Insert
    long addQuestion(QuestionEntity questionEntity);

    @Query("Select * from " + AppDataBase.TABLE.QUESTION_TABLE + " Where " + QuestionEntity.QUESTION_ID + " = :questionID")
    QuestionEntity getQuestion(long questionID);

    @Query("Select * from " + AppDataBase.TABLE.QUESTION_TABLE + " Where " + QuestionEntity.GAME_ID + " = :gameId")
    List<QuestionEntity> getQuestionForGame(long gameId);

    //---------------History---------------------
    @Insert
    long addHistory(HistoryEntity historyEntity);

    @Query("Select * from " + AppDataBase.TABLE.HISTORY_TABLE)
    List<HistoryEntity> getHistory();

    //---------------Selected Answer--------------
    @Insert
    long addSelectedAnswer(SelectedAnswerEntity selectedAnswerEntity);

    @Query("Select * from " + AppDataBase.TABLE.SELECTED_ANSWER_TABLE + " Where "
            + SelectedAnswerEntity.QUESTION_ID + " = :questionID"
            + " AND " +
            SelectedAnswerEntity.HISTORY_ID + " = :historyID")
    SelectedAnswerEntity getSelectedAnswer(long questionID, long historyID);
}

package com.appscrip.trivia.datamodel.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.appscrip.trivia.datamodel.local.AppDataBase;
import com.appscrip.trivia.datamodel.local.Converter;

import java.util.ArrayList;

@Entity(tableName = AppDataBase.TABLE.QUESTION_TABLE)
public class QuestionEntity {

    public static final String QUESTION_ID = "question_id";
    public static final String GAME_ID = "game_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = QUESTION_ID)
    private long questioID;

    @ColumnInfo(name = "Question")
    private String question;

    @ColumnInfo(name = "options")
    @TypeConverters(Converter.class)
    private ArrayList<String> options;

    @ColumnInfo(name = "correct_answers")
    @TypeConverters(Converter.class)
    private ArrayList<String> correctAnswers;

    @ColumnInfo(name = GAME_ID)
    private long gameID;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getQuestioID() {
        return questioID;
    }

    public void setQuestioID(long questioID) {
        this.questioID = questioID;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public ArrayList<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(ArrayList<String> correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public long getGameID() {
        return gameID;
    }

    public void setGameID(long gameID) {
        this.gameID = gameID;
    }
}

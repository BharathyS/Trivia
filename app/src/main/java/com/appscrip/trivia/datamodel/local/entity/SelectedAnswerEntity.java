package com.appscrip.trivia.datamodel.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.appscrip.trivia.datamodel.local.AppDataBase;
import com.appscrip.trivia.datamodel.local.Converter;

import java.util.ArrayList;

@Entity(tableName = AppDataBase.TABLE.SELECTED_ANSWER_TABLE)
public class SelectedAnswerEntity {
    public static final String SELECTED_ANSWER_ID = "selected_answer_id";
    public static final String QUESTION_ID = "question_id";
    public static final String HISTORY_ID = "history_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SELECTED_ANSWER_ID)
    private long selectedAnswerName;

    @ColumnInfo(name = "selected_answers")
    @TypeConverters(Converter.class)
    private ArrayList<String> selectedAnswers;

    @ColumnInfo(name = HISTORY_ID)
    private long HistoryID;

    @ColumnInfo(name = QUESTION_ID)
    private long questionID;

    public long getSelectedAnswerName() {
        return selectedAnswerName;
    }

    public void setSelectedAnswerName(long selectedAnswerName) {
        this.selectedAnswerName = selectedAnswerName;
    }

    public ArrayList<String> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(ArrayList<String> selectedAnswers) {
        this.selectedAnswers = selectedAnswers;
    }

    public long getHistoryID() {
        return HistoryID;
    }

    public void setHistoryID(long historyID) {
        HistoryID = historyID;
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }
}

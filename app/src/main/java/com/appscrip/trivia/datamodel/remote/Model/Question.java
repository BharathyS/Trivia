package com.appscrip.trivia.datamodel.remote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question {
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("options")
    @Expose
    private List<String> options = null;
    @SerializedName("correctAnswers")
    @Expose
    private List<String> correctAnswers = null;
    private boolean isAnsweredCorrectly = false;

    private long questionID;

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public boolean isAnsweredCorrectly() {
        return isAnsweredCorrectly;
    }

    public void setAnsweredCorrectly(boolean answeredCorrectly) {
        isAnsweredCorrectly = answeredCorrectly;
    }

    private List<String> selectedAnswerIndex = null;

    public List<String> getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public void setSelectedAnswerIndex(List<String> selectedAnswerIndex) {
        this.selectedAnswerIndex = selectedAnswerIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(List<String> correctAnswerIndex) {
        this.correctAnswers = correctAnswerIndex;
    }

}

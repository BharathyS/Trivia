package com.appscrip.trivia.datamodel.remote.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Game {
    @SerializedName("game_number")
    @Expose
    private Integer gameNumber;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    public String getGamename(){
        return "Game "+getGameNumber();
    }

    public Integer getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(Integer gameNumber) {
        this.gameNumber = gameNumber;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}

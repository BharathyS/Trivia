package com.appscrip.trivia.datamodel.remote.Model;

public class History
{
    private String gameName;
    private String timeString;
    private String QuestionAnswerString;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public String getQuestionAnswerString() {
        return QuestionAnswerString;
    }

    public void setQuestionAnswerString(String questionAnswerString) {
        QuestionAnswerString = questionAnswerString;
    }
}

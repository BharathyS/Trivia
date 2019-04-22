package com.appscrip.trivia.util;

import com.appscrip.trivia.datamodel.local.AppDao;
import com.appscrip.trivia.datamodel.local.entity.GameEntity;
import com.appscrip.trivia.datamodel.remote.Model.History;
import com.appscrip.trivia.datamodel.local.entity.HistoryEntity;
import com.appscrip.trivia.datamodel.local.entity.QuestionEntity;
import com.appscrip.trivia.datamodel.local.entity.SelectedAnswerEntity;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataBaseHelper {

    public static GameEntity getGameEntity(Game game) {
        GameEntity gameEntity = new GameEntity();
        gameEntity.setGameName("Game " + (game.getGameNumber() + 1));//Index basis so 1 is Incremented
        return gameEntity;
    }

    public static QuestionEntity getQuestEntity(Question question, long gameId) {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setGameID(gameId);
        questionEntity.setQuestion(question.getQuestion());
        questionEntity.setOptions((ArrayList<String>) question.getOptions());
        questionEntity.setCorrectAnswers((ArrayList<String>) question.getCorrectAnswers());
        return questionEntity;
    }

    public static SelectedAnswerEntity getSelectedAnswerEntity(Question question, long questionID, long historyID) {
        SelectedAnswerEntity selectedAnswerEntity = new SelectedAnswerEntity();
        selectedAnswerEntity.setHistoryID(historyID);
        selectedAnswerEntity.setQuestionID(questionID);
        selectedAnswerEntity.setSelectedAnswers((ArrayList<String>) question.getSelectedAnswerIndex());
        return selectedAnswerEntity;
    }

    public static HistoryEntity getHistoryEntity(long gameId, Date date) {
        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setDate(date);
        historyEntity.setGameId(gameId);
        return historyEntity;
    }

    public static void saveGameToLocalDb(Game game, AppDao appDao) {
        GameEntity gameEntity = getGameEntity(game);
        long gameId = appDao.addGame(gameEntity);

        for (int i = 0; i < game.getQuestions().size(); i++) {
            Question question = game.getQuestions().get(i);
            QuestionEntity questionEntity = getQuestEntity(question, gameId);
            long QuestionId = appDao.addQuestion(questionEntity);
        }
    }

    public static long getCurrentGameLevel(AppDao appDao) {
        return appDao.getCurrentGameLevelNumber();
    }

    public static List<History> getHistoryList(AppDao appDao) {
        List<History> historyList = new ArrayList<>();
        for (HistoryEntity historyEntity : appDao.getHistory()) {
            History history = new History();
            GameEntity gameEntity = appDao.getGame(historyEntity.getGameId());
            List<QuestionEntity> questionEntities = appDao.getQuestionForGame(gameEntity.getGameId());
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < questionEntities.size(); i++) {
                QuestionEntity questionEntity = questionEntities.get(i);
                if (i != 0) {
                    stringBuilder.append("\n");
                }
                stringBuilder.append("Q : " + questionEntity.getQuestion());
                stringBuilder.append("\n");
                SelectedAnswerEntity selectedAnswerEntity = appDao.getSelectedAnswer(questionEntity.getQuestioID(), historyEntity.getHistoryID());
                stringBuilder.append("A : ");
                stringBuilder.append(getselectedAnswerList(selectedAnswerEntity));
                stringBuilder.append("\n");
            }

            history.setGameName(gameEntity.getGameName());
            history.setTimeString(ConversionHelper.getformatedDateString(historyEntity.getDate(), "dd MMM yyyy hh:mm a"));
            history.setQuestionAnswerString(stringBuilder.toString());
            historyList.add(history);
        }

        return historyList;
    }

    private static String getselectedAnswerList(SelectedAnswerEntity selectedAnswerEntity) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < selectedAnswerEntity.getSelectedAnswers().size(); i++) {
            String option = selectedAnswerEntity.getSelectedAnswers().get(i);
            if (i != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(option);
        }
        return stringBuilder.toString();
    }
}

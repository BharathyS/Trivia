package com.appscrip.trivia.datamodel.local;

import com.appscrip.trivia.datamodel.DataSource;
import com.appscrip.trivia.datamodel.local.entity.GameEntity;
import com.appscrip.trivia.datamodel.remote.Model.History;
import com.appscrip.trivia.datamodel.local.entity.QuestionEntity;
import com.appscrip.trivia.datamodel.local.entity.SelectedAnswerEntity;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;
import com.appscrip.trivia.datamodel.remote.Model.Question;
import com.appscrip.trivia.util.AppExecutors;
import com.appscrip.trivia.util.DataBaseHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;
    private AppExecutors appExecutors;
    private AppDao appDao;

    private LocalDataSource(AppExecutors appExecutors, AppDao appDao) {
        this.appExecutors = appExecutors;
        this.appDao = appDao;
    }

    static public LocalDataSource getInstance(AppExecutors appExecutors, AppDao appDao) {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource(appExecutors, appDao);
        }
        return INSTANCE;
    }

    @Override
    public void getGame(final GameRequest gameRequest, final IGameResponseCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Game game = new Game();
                GameEntity gameEntity = appDao.getGame(gameRequest.getGameNumber());
                game.setGameNumber((int) gameEntity.getGameId());
                List<QuestionEntity> questionEntities = appDao.getQuestionForGame(gameEntity.getGameId());
                List<Question> questions = new ArrayList<>();
                for (int i = 0; i < questionEntities.size(); i++) {
                    QuestionEntity questionEntity = questionEntities.get(i);
                    Question question = new Question();
                    question.setQuestion(questionEntity.getQuestion());
                    question.setCorrectAnswers(questionEntity.getCorrectAnswers());
                    question.setOptions(questionEntity.getOptions());
                    question.setQuestionID(questionEntity.getQuestioID());
                    questions.add(question);
                }
                game.setQuestions(questions);

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onGameResponseReceive(game);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void addHistory(final Game game, final IHistoryAddedCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long historyId = appDao.addHistory(DataBaseHelper.getHistoryEntity(game.getGameNumber(), new Date()));
                for (Question question : game.getQuestions()) {
                    long questionId = question.getQuestionID();
                    SelectedAnswerEntity selectedAnswerEntity = DataBaseHelper.getSelectedAnswerEntity(question, questionId, historyId);
                    appDao.addSelectedAnswer(selectedAnswerEntity);
                }

                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onHistoryAddSuccefully("Successfully Added");
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getHistory(final IHistoryListReceiveCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<History> historyList = DataBaseHelper.getHistoryList(appDao);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onHistoryListResponseReceive(historyList);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getMaxGameLevel(IMaximumGameLeveResponse maximumGameLeveResponse) {

    }

    public void addToLocalDB(final Game game, final ISaveGameDataToLocalDBCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                DataBaseHelper.saveGameToLocalDb(game, appDao);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onCompleted();
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    public void checkGameSavedLocally(final int gameLevelNumber, final IGameAlreadyExsistCallBack callBack) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final boolean state;
                if ((DataBaseHelper.getCurrentGameLevel(appDao)) < gameLevelNumber) {
                    state = false;
                } else {
                    state = true;
                }
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        callBack.gameAlreadyExsist(state);
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }
}

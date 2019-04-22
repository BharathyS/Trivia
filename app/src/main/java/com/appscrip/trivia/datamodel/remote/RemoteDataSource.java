package com.appscrip.trivia.datamodel.remote;

import com.appscrip.trivia.datamodel.DataSource;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;

public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE;
    private ApiService apiService;

    private RemoteDataSource(ApiService apiService) {
        this.apiService = apiService;
    }

    public static RemoteDataSource getInstance(ApiService apiService) {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource(apiService);
        }
        return INSTANCE;
    }

    @Override
    public void getGame(GameRequest gameRequest, IGameResponseCallBack callBack) {
        apiService.getGame(gameRequest, callBack);
    }

    @Override
    public void addHistory(Game game, IHistoryAddedCallBack callBack) {

    }

    @Override
    public void getHistory(IHistoryListReceiveCallBack callBack) {

    }

    @Override
    public void getMaxGameLevel(IMaximumGameLeveResponse callBack) {
        apiService.getMaximumGameLevel(callBack);
    }
}

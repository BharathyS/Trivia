package com.appscrip.trivia.datamodel;


import com.appscrip.trivia.datamodel.local.LocalDataSource;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;
import com.appscrip.trivia.datamodel.remote.RemoteDataSource;

public class Repository implements DataSource {

    static private Repository INSTANCE;
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    private Repository(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static Repository getInstance(LocalDataSource localDataSource, RemoteDataSource remoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(localDataSource, remoteDataSource);
        }
        return INSTANCE;
    }

    /**
     * Repository Checks for the GameLevel Data Exist in Local DB.
     * IF not fetched it from FireBase DataBase updates the Local DB
     * Data sent to viewModel from LocalDB
     */

    @Override
    public void getGame(final GameRequest gameRequest, final IGameResponseCallBack callBack) {
        localDataSource.checkGameSavedLocally(gameRequest.getGameNumber(), new IGameAlreadyExsistCallBack() {
            @Override
            public void gameAlreadyExsist(boolean isExsit) {
                if (isExsit) {
                    localDataSource.getGame(gameRequest, callBack);
                } else {
                    fetchGameFromFireBase(gameRequest, callBack);
                }
            }
        });
    }

    private void fetchGameFromFireBase(final GameRequest gameRequest, final IGameResponseCallBack callBack) {
        remoteDataSource.getGame(gameRequest, new IGameResponseCallBack() {
            @Override
            public void onGameResponseReceive(final Game game) {
                saveGameToLocalDataBase(game, gameRequest, callBack);
            }

            @Override
            public void onGameResponseError(String error) {
                callBack.onGameResponseError(error);
            }
        });
    }

    private void saveGameToLocalDataBase(Game game, final GameRequest gameRequest, final IGameResponseCallBack callBack) {
        localDataSource.addToLocalDB(game, new ISaveGameDataToLocalDBCallBack() {
            @Override
            public void onCompleted() {
                localDataSource.getGame(gameRequest, callBack);
            }
        });
    }

    @Override
    public void addHistory(Game game, IHistoryAddedCallBack callBack) {
        localDataSource.addHistory(game, callBack);
    }

    @Override
    public void getHistory(IHistoryListReceiveCallBack callBack) {
        localDataSource.getHistory(callBack);
    }

    @Override
    public void getMaxGameLevel(IMaximumGameLeveResponse maximumGameLeveResponse) {
        remoteDataSource.getMaxGameLevel(maximumGameLeveResponse);
    }
}

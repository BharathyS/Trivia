package com.appscrip.trivia.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.appscrip.trivia.datamodel.DataSource;
import com.appscrip.trivia.datamodel.Repository;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;
import com.appscrip.trivia.util.Injection;

public class GameViewModel extends AndroidViewModel implements DataSource.IGameResponseCallBack
        , DataSource.IHistoryAddedCallBack {

    private Repository repository;
    private MutableLiveData<Game> game = new MutableLiveData<>();
    private MutableLiveData<String> onGameResponseError = new MutableLiveData<>();
    private MutableLiveData<String> onHistoryAddedSuccess = new MutableLiveData<>();
    private MutableLiveData<String> onHistoryAddedError = new MutableLiveData<>();

    public GameViewModel(@NonNull Application application) {
        super(application);
        this.repository = Injection.getRepository(application);
    }

    public MutableLiveData<String> getOnHistoryAddedSuccess() {
        return onHistoryAddedSuccess;
    }

    public MutableLiveData<String> getOnHistoryAddedError() {
        return onHistoryAddedError;
    }

    public void addGameToHistory(Game game) {
        repository.addHistory(game, this);
    }

    public MutableLiveData<Game> getGame() {
        return game;
    }

    public MutableLiveData<String> getOnGameResponseError() {
        return onGameResponseError;
    }

    @Override
    public void onGameResponseReceive(Game game) {
        this.game.setValue(game);
    }

    @Override
    public void onGameResponseError(String error) {
        this.onGameResponseError.setValue(error);
    }

    public void getGameForDataBase(GameRequest gameRequest) {
        repository.getGame(gameRequest, this);
    }

    @Override
    public void onHistoryAddSuccefully(String state) {
        onHistoryAddedSuccess.postValue(state);
    }

    @Override
    public void onHistoryAddError(String error) {
        onHistoryAddedError.postValue(error);
    }
}

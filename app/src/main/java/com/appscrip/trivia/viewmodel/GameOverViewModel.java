package com.appscrip.trivia.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.appscrip.trivia.datamodel.DataSource;
import com.appscrip.trivia.datamodel.Repository;
import com.appscrip.trivia.util.Constant;
import com.appscrip.trivia.util.DataBaseHelper;
import com.appscrip.trivia.util.Injection;
import com.appscrip.trivia.util.PrefferenceManager;
import com.appscrip.trivia.view.GameOverActivity;

public class GameOverViewModel extends AndroidViewModel implements DataSource.IMaximumGameLeveResponse {

    public MutableLiveData<Boolean> isNextLevelAvailable = new MutableLiveData<>();
    public MutableLiveData<String> onError = new MutableLiveData<>();
    private Repository repository;
    private Application application;


    public void getMaxGameLevelValue() {
        repository.getMaxGameLevel(this);
    }

    public GameOverViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.repository = Injection.getRepository(application);
    }

    @Override
    public void onMaxGameLevelResponse(int gameNumber) {
        if (gameNumber > PrefferenceManager.getGameNumber(application)) {
            isNextLevelAvailable.postValue(true);
        } else {
            isNextLevelAvailable.postValue(false);
        }
    }

    @Override
    public void omMaxGameLevelResponseError(String error) {
        onError.postValue(error);
        int nextLevelValue = PrefferenceManager.getGameNumber(application);
        PrefferenceManager.setGameNumber(application, --nextLevelValue);
    }
}

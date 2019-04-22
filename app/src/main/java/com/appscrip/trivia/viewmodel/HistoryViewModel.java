package com.appscrip.trivia.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.appscrip.trivia.datamodel.DataSource;
import com.appscrip.trivia.datamodel.Repository;
import com.appscrip.trivia.datamodel.remote.Model.History;
import com.appscrip.trivia.util.Injection;

import java.util.List;

public class HistoryViewModel extends AndroidViewModel implements DataSource.IHistoryListReceiveCallBack {

    private final Repository repository;
    private MutableLiveData<List<History>> historyList = new MutableLiveData<>();
    private MutableLiveData<String> onError = new MutableLiveData<>();

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = Injection.getRepository(application);
    }

    public void getHistoryListFormDataBase() {
        repository.getHistory(this);
    }

    public MutableLiveData<List<History>> getHistoryList() {
        return historyList;
    }

    public MutableLiveData<String> getOnError() {
        return onError;
    }


    @Override
    public void onHistoryListResponseReceive(List<History> histories) {
        historyList.postValue(histories);
    }

    @Override
    public void onHistoryListResponseError(String error) {
        onError.postValue(error);
    }
}

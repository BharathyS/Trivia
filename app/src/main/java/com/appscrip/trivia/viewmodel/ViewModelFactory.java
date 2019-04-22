package com.appscrip.trivia.viewmodel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.appscrip.trivia.datamodel.Repository;
import com.appscrip.trivia.util.Injection;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static ViewModelFactory INSTANCE;
    private Application application;
    private Repository repository;

    private ViewModelFactory(Application application, Repository redBusRepository) {
        this.application = application;
        this.repository = redBusRepository;
    }

    static public ViewModelFactory getIntance(Application application) {
        if (INSTANCE == null) {
            INSTANCE = new ViewModelFactory(application, Injection.getRepository(application));
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GameViewModel.class)) {
            return (T) new GameViewModel(application);
        } else if (modelClass.isAssignableFrom(HistoryViewModel.class)) {
            return (T) new HistoryViewModel(application);
        }
        else if(modelClass.isAssignableFrom(GameOverViewModel.class)){
            return (T) new GameOverViewModel(application);
        }
        return super.create(modelClass);
    }
}

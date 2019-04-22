package com.appscrip.trivia.util;

import android.app.Application;
import android.content.Context;

import com.appscrip.trivia.datamodel.Repository;
import com.appscrip.trivia.datamodel.local.AppDataBase;
import com.appscrip.trivia.datamodel.local.LocalDataSource;
import com.appscrip.trivia.datamodel.remote.Client;
import com.appscrip.trivia.datamodel.remote.RemoteDataSource;

public class Injection {
    public static Repository getRepository(Application context) {
        AppDataBase appDataBase = AppDataBase.getInstance(context);
        Repository repository = Repository.getInstance(LocalDataSource.getInstance(new AppExecutors(), appDataBase.appDao())
                , RemoteDataSource.getInstance(Client.getApiservice(context)));
        return repository;
    }
}

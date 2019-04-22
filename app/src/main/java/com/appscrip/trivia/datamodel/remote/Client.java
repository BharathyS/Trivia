package com.appscrip.trivia.datamodel.remote;

import android.app.Application;

public class Client {

    public static ApiService getApiservice(Application application) {
        return FireBaseApiService.getInstance(application);
    }
}

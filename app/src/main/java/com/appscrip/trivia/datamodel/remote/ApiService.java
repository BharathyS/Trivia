package com.appscrip.trivia.datamodel.remote;

import com.appscrip.trivia.datamodel.DataSource;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;

public interface ApiService
{
    void getGame(GameRequest gameRequest, DataSource.IGameResponseCallBack callBack);
    void getMaximumGameLevel(DataSource.IMaximumGameLeveResponse callback);
}

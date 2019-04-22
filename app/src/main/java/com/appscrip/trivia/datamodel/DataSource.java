package com.appscrip.trivia.datamodel;

import com.appscrip.trivia.datamodel.remote.Model.History;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;

import java.util.List;

public interface DataSource {
    void getGame(GameRequest gameRequest, IGameResponseCallBack callBack);

    void addHistory(Game game, IHistoryAddedCallBack callBack);

    void getHistory(IHistoryListReceiveCallBack callBack);

    void getMaxGameLevel(IMaximumGameLeveResponse maximumGameLeveResponse);

    interface ISaveGameDataToLocalDBCallBack{
        void onCompleted();
    }

    interface IGameAlreadyExsistCallBack{
        void gameAlreadyExsist(boolean state);
    }

    interface IMaximumGameLeveResponse {
        void onMaxGameLevelResponse(int gameNumber);

        void omMaxGameLevelResponseError(String error);
    }

    interface IGameResponseCallBack {
        void onGameResponseReceive(Game game);

        void onGameResponseError(String error);
    }

    interface IHistoryAddedCallBack {
        void onHistoryAddSuccefully(String state);

        void onHistoryAddError(String error);
    }

    interface IHistoryListReceiveCallBack {
        void onHistoryListResponseReceive(List<History> histories);

        void onHistoryListResponseError(String error);
    }
}

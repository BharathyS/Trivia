package com.appscrip.trivia.datamodel.remote;

import android.app.Application;
import android.support.annotation.NonNull;

import com.appscrip.trivia.datamodel.DataSource;
import com.appscrip.trivia.datamodel.remote.Model.Game;
import com.appscrip.trivia.datamodel.remote.Model.GameRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FireBaseApiService implements ApiService {

    public static FireBaseApiService INSTANCE;
    private final DatabaseReference mDatabase;
    private Application context;

    private FireBaseApiService(Application context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public static FireBaseApiService getInstance(Application context) {
        if (INSTANCE == null) {
            INSTANCE = new FireBaseApiService(context);
        }
        return INSTANCE;
    }

    @Override
    public void getGame(GameRequest gameRequest, final DataSource.IGameResponseCallBack callBack) {
        String gameNmber = String.valueOf((gameRequest.getGameNumber())-1);
        mDatabase.child("trivia").child("game").child(gameNmber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Game game = dataSnapshot.getValue(Game.class);
                callBack.onGameResponseReceive(game);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
                callBack.onGameResponseError(databaseError.getMessage());
            }
        });
    }

    @Override
    public void getMaximumGameLevel(final DataSource.IMaximumGameLeveResponse callback) {
        mDatabase.child("trivia").child("max_game_level").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                callback.onMaxGameLevelResponse(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
                callback.omMaxGameLevelResponseError(databaseError.getMessage());
            }
        });
    }


}

package com.appscrip.trivia.datamodel.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.appscrip.trivia.datamodel.local.AppDataBase;

import java.util.Date;

@Entity(tableName = AppDataBase.TABLE.GAME_TABLE)
public class GameEntity {

    public static final String GAME_ID="game_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = GAME_ID)
    private long gameId;

    @ColumnInfo(name = "game_name")
    private String gameName;

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}

package com.appscrip.trivia.datamodel.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.appscrip.trivia.datamodel.local.AppDataBase;
import com.appscrip.trivia.datamodel.local.Converter;

import java.util.Date;

@Entity(tableName = AppDataBase.TABLE.HISTORY_TABLE)
public class HistoryEntity {
    public static final String HISTROY_ID = "history_id";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = HISTROY_ID)
    private long historyID;

    @ColumnInfo(name = "game_id")
    private long gameId;

    @ColumnInfo(name = "_date")
    @TypeConverters(Converter.class)
    private Date date;

    public long getHistoryID() {
        return historyID;
    }

    public void setHistoryID(long historyID) {
        this.historyID = historyID;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

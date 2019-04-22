package com.appscrip.trivia.datamodel.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.appscrip.trivia.datamodel.local.entity.GameEntity;
import com.appscrip.trivia.datamodel.local.entity.HistoryEntity;
import com.appscrip.trivia.datamodel.local.entity.QuestionEntity;
import com.appscrip.trivia.datamodel.local.entity.SelectedAnswerEntity;

@Database(entities = {GameEntity.class, HistoryEntity.class, QuestionEntity.class, SelectedAnswerEntity.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;
    private final static String DATABASE_NAME = "appdatabase.db";

    public static AppDataBase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME).build();
        }
        return INSTANCE;
    }

    public abstract AppDao appDao();

    public static class TABLE {
        final static public String GAME_TABLE = "game_table";
        final static public String HISTORY_TABLE = "history_table";
        final static public String QUESTION_TABLE = "question_table";
        final static public String SELECTED_ANSWER_TABLE = "selected_answer_table";
    }
}

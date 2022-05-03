package com.ru.mirea.kalugina.mireaproject.ui.history.db;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    public static App instance;
    private HistoryDatabase database;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, HistoryDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }
    public static App getInstance() {
        return instance;
    }
    public HistoryDatabase getDatabase() {
        return database;
    }
}

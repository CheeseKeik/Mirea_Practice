package com.ru.mirea.kalugina.mireaproject.ui.history.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {History.class}, version = 2)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();
}

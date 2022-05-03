package com.ru.mirea.kalugina.mireaproject.ui.history.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History {
    @PrimaryKey(autoGenerate = true)
    public int id = 0;
    public String title;
    public String content;

    public History(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

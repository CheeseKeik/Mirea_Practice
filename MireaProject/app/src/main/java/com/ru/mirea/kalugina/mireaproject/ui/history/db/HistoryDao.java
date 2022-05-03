package com.ru.mirea.kalugina.mireaproject.ui.history.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("SELECT * FROM History")
    List<History> getAll();

    @Query("SELECT * FROM History WHERE title LIKE :title LIMIT 1")
    History findByTitle(String title);

    @Query("SELECT * FROM History WHERE id = (:id)")
    History getById(int id);

    @Insert
    void insertAll(History... histories);

    @Insert
    void insert(History history);

    @Update
    void update(History history);

    @Delete
    void delete(History history);

    @Query("DELETE FROM History")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM History")
    int getCount();
}

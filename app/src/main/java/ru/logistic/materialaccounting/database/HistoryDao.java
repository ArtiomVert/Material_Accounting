package ru.logistic.materialaccounting.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(History history);

    @Query("SELECT * FROM history")
    LiveData<List<History>> getAllItems();
}

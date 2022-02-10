package ru.logistic.materialaccounting.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.logistic.materialaccounting.Category;


@Dao
public interface StorageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Delete
    void delete(Category category);

    @Query("DELETE FROM categories")
    void deleteAllCategories();

    @Query("SELECT * FROM categories WHERE id = :id ")
    LiveData<Category> getCategory(long id);

    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAllCategories();
}

package ru.logistic.materialaccounting.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItem(Item item);

    @Delete
    void delete(Item i);

    @Query("DELETE FROM items")
    void deleteAllItems();

    @Query("SELECT * FROM items WHERE id = :id ")
    LiveData<Item> getItem(long id);

    @Query("SELECT * FROM items WHERE idcategory = :idcategory")
    LiveData<List<Item>> getAllItemsByIdCategory(long idcategory);

    @Query("SELECT * FROM items")
    LiveData<List<Item>> getAllItems();
}

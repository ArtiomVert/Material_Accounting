package ru.logistic.materialaccounting.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class, Category.class, History.class}, version = 1, exportSchema = false)
public abstract class DatabaseHelper extends RoomDatabase {

    public abstract ItemsDao itemDao();
    public abstract StorageDao categoryDao();
    public abstract HistoryDao historyDao();

    private static ru.logistic.materialaccounting.database.DatabaseHelper instance;

    public synchronized static ru.logistic.materialaccounting.database.DatabaseHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    ctx.getApplicationContext(), ru.logistic.materialaccounting.database.DatabaseHelper.class,
                    "Database.bd")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
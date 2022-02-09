package ru.logistic.materialaccounting;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.logistic.materialaccounting.database.ItemsDao;


@Database(entities = Item.class, version = 1, exportSchema = false)
public abstract class ItemDatabase extends RoomDatabase {

    public abstract ItemsDao itemDao();

    private static ItemDatabase instance;

    public synchronized static ItemDatabase getInstance(Context ctx) {
        if (instance == null){
            instance = Room.databaseBuilder(
                    ctx.getApplicationContext(), ItemDatabase.class,
                    "ItemDatabase.bd")
                    .fallbackToDestructiveMigration()
                    .build();}
        return instance;
    }


}
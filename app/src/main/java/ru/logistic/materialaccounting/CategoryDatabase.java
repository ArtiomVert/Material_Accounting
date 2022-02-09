package ru.logistic.materialaccounting;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ru.logistic.materialaccounting.database.StorageDao;


@Database(entities = Category.class, version = 1, exportSchema = false)
public abstract class CategoryDatabase extends RoomDatabase {

    public abstract StorageDao categoryDao();

    private static CategoryDatabase instance;

    synchronized public static CategoryDatabase getInstance(Context ctx) {
        if (instance == null){
            instance = Room.databaseBuilder(
                    ctx.getApplicationContext(), CategoryDatabase.class,
                    "myDatabase.bd")
                    .fallbackToDestructiveMigration()
                    .build();}
        return instance;
    }


}

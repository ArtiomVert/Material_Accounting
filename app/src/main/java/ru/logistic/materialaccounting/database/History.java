package ru.logistic.materialaccounting.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class History {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String time;

    @ColumnInfo
    public String action;

    @ColumnInfo
    public String details;

    public History(long id, String time, String action, String details) {
        this.time = time;
        this.id = id;
        this.action = action;
        this.details = details;

    }
}
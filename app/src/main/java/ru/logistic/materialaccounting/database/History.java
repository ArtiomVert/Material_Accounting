package ru.logistic.materialaccounting.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "history")
public class History {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String action;

    @ColumnInfo
    public String details;

    public History(long id, String name, String action, String details) {
        this.name = name;
        this.id = id;
        this.action = action;
        this.details = details;

    }
}
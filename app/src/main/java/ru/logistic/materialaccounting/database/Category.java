package ru.logistic.materialaccounting.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String image;

    public Category(long id, String name, String image) {
        this.name = name;
        this.id = id;
        this.image = image;

    }
}
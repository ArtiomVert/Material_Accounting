package ru.logistic.materialaccounting;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String content;

    @ColumnInfo
    public long idcategory;

    @ColumnInfo
    public String image;

    public Item(long id, long idcategory, String name, String content, String image) {
        this.name = name;
        this.content = content;
        this.id = id;
        this.idcategory = idcategory;
        this.image = image;
    }
}

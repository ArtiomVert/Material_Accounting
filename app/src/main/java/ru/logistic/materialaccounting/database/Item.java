package ru.logistic.materialaccounting.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String content;

    @ColumnInfo
    public int count;

    @ColumnInfo
    public long idcategory;

    @ColumnInfo
    public String image;

    @ColumnInfo
    public String link;

    @ColumnInfo
    public String stat;

    @ColumnInfo
    public String mera;

    @ColumnInfo
    public int popularityusing = 0;

    @ColumnInfo
    public String photos;

    public Item(long idcategory, String name, String content, int count, String image, String link, String mera) {
        this.name = name;
        this.content = content;
        this.count = count;
        this.idcategory = idcategory;
        this.image = image;
        this.link = link;
        this.stat = count + "";
        this.mera = mera;
        this.photos = image;
    }
}

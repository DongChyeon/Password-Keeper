package com.dongchyeon.passwordkeeper.database.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Category")
public class Category {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "type")
    private String type;

    public Category(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}

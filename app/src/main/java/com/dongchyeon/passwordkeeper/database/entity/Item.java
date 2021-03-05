package com.dongchyeon.passwordkeeper.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Items")
public class Item {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;    // 엔터티 아이디 (기본키)
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "memo")
    private String memo;

    public Item(String title, String category, String uid, String password, String memo) {
        this.title = title;
        this.category = category;
        this.uid = uid;
        this.password = password;
        this.memo = memo;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getMemo() { return memo; }

    public void setMemo(String memo) { this.memo = memo; }
}

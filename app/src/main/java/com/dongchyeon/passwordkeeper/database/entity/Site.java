package com.dongchyeon.passwordkeeper.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Sites")
public class Site {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;    // 엔터티 아이디 (기본키)
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "url")
    private String url;

    public Site(String title, String uid, String password, String url) {
        this.title = title;
        this.uid = uid;
        this.password = password;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

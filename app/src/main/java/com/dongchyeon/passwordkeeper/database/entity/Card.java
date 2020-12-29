package com.dongchyeon.passwordkeeper.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Cards")
public class Card {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;    // 엔터티 아이디 (기본키)
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "uid")
    private String uid;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "message")
    private String message;
    @ColumnInfo(name = "pin")
    private String pin;
    @ColumnInfo(name = "company")
    private String company;

    public Card(String title, String uid, String password, String message, String pin, String company) {
        this.title = title;
        this.uid = uid;
        this.password = password;
        this.message = message;
        this.pin = pin;
        this.company = company;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}

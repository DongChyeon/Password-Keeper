package com.dongchyeon.passwordkeeper.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {
    @PrimaryKey(autoGenerate = true)
    private int eid;    // 엔터티 아이디 (기본키)
    private String title;
    private String id;
    private String password;
    private String message;
    private String pin;
    private String company;

    public Card(String title, String id, String password, String message, String pin, String company) {
        this.title = title;
        this.id = id;
        this.password = password;
        this.message = message;
        this.pin = pin;
        this.company = company;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setMessage(String url) {
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

    @Override
    public String toString() {
        return "Card{" +
                "eid=" + eid +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", message='" + message + '\'' +
                ", pin='" + pin + '\'' +
                ", company='" + company + '\'' +
                '}';
    }
}

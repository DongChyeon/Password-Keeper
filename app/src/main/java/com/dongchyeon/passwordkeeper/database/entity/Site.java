package com.dongchyeon.passwordkeeper.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Site {
    @PrimaryKey(autoGenerate = true)
    private int eid;    // 엔터티 아이디 (기본키)
    private String title;
    private String id;
    private String password;
    private String url;

    public Site(String title, String id, String password, String url) {
        this.title = title;
        this.id = id;
        this.password = password;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Site{" +
                "eid=" + eid +
                ", title='" + title + '\'' +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

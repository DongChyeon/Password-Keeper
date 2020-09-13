package com.dongchyeon.passwordkeeper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.database.entity.Site;

@Database(entities = {Site.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SiteDao siteDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "app-db").build();
        }
        return instance;
    }
}

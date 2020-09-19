package com.dongchyeon.passwordkeeper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.database.entity.Site;

@Database(entities = {Site.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SiteDao siteDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-db").build();
            }
        }
        return instance;
    }
}

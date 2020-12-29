package com.dongchyeon.passwordkeeper.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.dongchyeon.passwordkeeper.database.dao.CardDao;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.database.entity.Card;
import com.dongchyeon.passwordkeeper.database.entity.Site;

@Database(entities = {Site.class, Card.class}, version = 3, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SiteDao siteDao();
    public abstract CardDao cardDao();

    private static volatile AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-db")
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();
            }
        }
        return instance;
    }

    // 데이터베이스 버전 1에서 2로 업데이트할 시 이전 사항
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS Sites (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "title TEXT, " +
                            "uid TEXT, " +
                            "password TEXT, " +
                            "url TEXT)"
            );
            database.execSQL("INSERT INTO Sites (id, title, uid, password, url) " +
                    "SELECT eid, title, id, password, url FROM site");
            database.execSQL("DROP TABLE Site");

            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS Cards (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "title TEXT, " +
                            "uid TEXT, " +
                            "password TEXT, " +
                            "message TEXT, " +
                            "pin TEXT, " +
                            "company TEXT)"
            );
            database.execSQL("INSERT INTO Cards (id, title, uid, password, message, pin, company)" +
                    "SELECT eid, title, id, password, message, pin, company FROM card");
            database.execSQL("DROP TABLE Card");
        }
    };

    // 데이터베이스 버전 2에서 3로 업데이트할 시 이전 사항
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS new_Sites (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "title TEXT, " +
                            "uid TEXT, " +
                            "password TEXT, " +
                            "url TEXT)"
            );
            database.execSQL("INSERT INTO new_Sites (id, title, uid, password, url) " +
                    "SELECT id, title, uid, password, url FROM sites");
            database.execSQL("DROP TABLE sites");
            database.execSQL("ALTER TABLE new_Sites RENAME TO Sites");

            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS new_Cards (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "title TEXT, " +
                            "uid TEXT, " +
                            "password TEXT, " +
                            "message TEXT, " +
                            "pin TEXT, " +
                            "company TEXT)"
            );
            database.execSQL("INSERT INTO new_Cards (id, title, uid, password, message, pin, company)" +
                    "SELECT id, title, uid, password, message, pin, company FROM cards");
            database.execSQL("DROP TABLE cards");
            database.execSQL("ALTER TABLE new_Cards RENAME TO Cards");
        }
    };
}

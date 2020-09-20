package com.dongchyeon.passwordkeeper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dongchyeon.passwordkeeper.database.entity.Site;

import java.util.List;

@Dao
public interface SiteDao {
    @Query("SELECT * FROM site")
    LiveData<List<Site>> getAll();

    @Insert
    void insert(Site site);

    @Update
    void update(Site site);

    @Delete
    void delete(Site site);

    @Query("SELECT * FROM site WHERE eid = :id")
    Site getItemByEid(int id);
}

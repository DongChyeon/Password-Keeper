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
    @Query("SELECT * FROM Sites")
    LiveData<List<Site>> getAll();

    @Insert
    void insert(Site site);

    @Update
    void update(Site site);

    @Delete
    void delete(Site site);

    @Query("SELECT * FROM Sites WHERE id = :id")
    Site getItemById(int id);
}

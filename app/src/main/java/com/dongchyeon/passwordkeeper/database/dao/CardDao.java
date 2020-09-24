package com.dongchyeon.passwordkeeper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dongchyeon.passwordkeeper.database.entity.Card;

import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM card")
    LiveData<List<Card>> getAll();

    @Insert
    void insert(Card card);

    @Update
    void update(Card card);

    @Delete
    void delete(Card card);

    @Query("SELECT * FROM card WHERE eid = :id")
    Card getItemByEid(int id);
}

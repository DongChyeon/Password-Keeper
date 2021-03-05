package com.dongchyeon.passwordkeeper.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dongchyeon.passwordkeeper.database.entity.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM Items")
    LiveData<List<Item>> getAll();

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM Items WHERE id = :id")
    Item getItemById(int id);

    @Query("SELECT * FROM Items WHERE category = :category")
    LiveData<List<Item>> getItemsByCategory(String category);

    @Query("SELECT DISTINCT category FROM Items")
    LiveData<List<String>> getCategories();
}

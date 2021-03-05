package com.dongchyeon.passwordkeeper.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.ItemDao;
import com.dongchyeon.passwordkeeper.database.entity.Item;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ItemRepository {
    private ItemDao itemDao;
    private LiveData<List<Item>> items;

    public ItemRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        itemDao = db.itemDao();
        items = itemDao.getAll();
    }

    public LiveData<List<Item>> getItems() {
        return items;
    }

    // 아이템 삽입
    public void insert(final Item item) {
        Runnable addRunnable = () -> itemDao.insert(item);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // 아이템 수정
    public void update(long id, String title, String category, String uid, String pw, String memo) {
        Runnable addRunnable = () -> {
            Item item = itemDao.getItemById(id);
            item.setTitle(title);
            item.setCategory(category);
            item.setUid(uid);
            item.setPassword(pw);
            item.setMemo(memo);
            itemDao.update(item);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // 아이템 삭제
    public void delete(long id) {
        Runnable addRunnable = () -> itemDao.delete(itemDao.getItemById(id));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    public void getItemsByCategory(String category) {
        items = itemDao.getItemsByCategory(category);
    }
}

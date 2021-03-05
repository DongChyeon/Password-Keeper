package com.dongchyeon.passwordkeeper.database.repository;

import android.app.Application;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CategoryDao;
import com.dongchyeon.passwordkeeper.database.entity.Category;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private List<Category> items;
    private List<String> titles;

    public CategoryRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        categoryDao = db.categoryDao();
    }

    public List<Category> getItems() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                items = categoryDao.getAll();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<String> getTitles() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                titles = categoryDao.getTitles();
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return titles;
    }

    // 카테고리 삽입
    public void insert(final Category category) {
        Runnable addRunnable = () -> categoryDao.insert(category);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // 카테고리 수정
    public void update(String title, String type) {
        Runnable addRunnable = () -> {
            Category category = categoryDao.getItemByTitle(title);
            category.setTitle(title);
            category.setType(type);
            categoryDao.update(category);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // 아이템 삭제
    public void delete(String title) {
        Runnable addRunnable = () -> categoryDao.delete(categoryDao.getItemByTitle(title));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}

package com.dongchyeon.passwordkeeper.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.database.entity.Site;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SiteRepository {
    private SiteDao siteDao;
    private LiveData<List<Site>> sites;

    public SiteRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        siteDao = db.siteDao();
        sites = siteDao.getAll();
    }

    public LiveData<List<Site>> getSites() {
        return sites;
    }

    // Site 아이템 삽입
    public void insert(final Site site) {
        Runnable addRunnable = () -> siteDao.insert(site);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // Site 아이템 수정
    public void update(int id, String title, String uid, String pw, String url) {
        Runnable addRunnable = () -> {
            Site site = siteDao.getItemById(id);
            site.setTitle(title);
            site.setUid(uid);
            site.setPassword(pw);
            site.setUrl(url);
            siteDao.update(site);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // Site 아이템 삽입
    public void delete(int id) {
        Runnable addRunnable = () -> siteDao.delete(siteDao.getItemById(id));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}

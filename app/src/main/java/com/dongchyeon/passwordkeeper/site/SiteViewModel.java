package com.dongchyeon.passwordkeeper.site;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dongchyeon.passwordkeeper.database.entity.Site;
import com.dongchyeon.passwordkeeper.database.repository.SiteRepository;

import java.util.List;

public class SiteViewModel extends AndroidViewModel {
    private SiteRepository repository;
    private LiveData<List<Site>> sites;

    public SiteViewModel(Application application) {
        super(application);
        repository = new SiteRepository(application);
        sites = repository.getSites();
    }

    public LiveData<List<Site>> getSites() {
        return sites;
    }

    public void insert(Site site) {
        repository.insert(site);
    }

    public void update(int id, String title, String uid, String pw, String url) {
        repository.update(id, title, uid, pw, url);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}

package com.dongchyeon.passwordkeeper.item;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dongchyeon.passwordkeeper.database.entity.Item;
import com.dongchyeon.passwordkeeper.database.repository.ItemRepository;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private ItemRepository repository;
    private LiveData<List<Item>> items;

    public ItemViewModel(Application application) {
        super(application);
        repository = new ItemRepository(application);
        items = repository.getItems();
    }

    public LiveData<List<Item>> getItems() {
        return items;
    }

    public void getItemsByCategory(String category) {
        repository.getItemsByCategory(category);
        items = repository.getItems();
    }

    public void insert(Item item) {
        repository.insert(item);
    }

    public void update(long id, String title, String category, String uid, String pw, String memo) {
        repository.update(id, title, category, uid, pw, memo);
    }

    public void delete(long id) {
        repository.delete(id);
    }
}

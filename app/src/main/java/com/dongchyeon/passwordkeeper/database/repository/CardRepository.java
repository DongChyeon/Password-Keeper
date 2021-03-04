package com.dongchyeon.passwordkeeper.database.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CardDao;
import com.dongchyeon.passwordkeeper.database.entity.Card;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CardRepository {
    private CardDao cardDao;
    private LiveData<List<Card>> cards;

    public CardRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        cardDao = db.cardDao();
        cards = cardDao.getAll();
    }

    public LiveData<List<Card>> getCards() {
        return cards;
    }

    // Card 아이템 삽입
    public void insert(final Card card) {
        Runnable addRunnable = () -> cardDao.insert(card);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // Card 아이템 수정
    public void update(int id, String title, String uid, String pw, String msg, String pin, String company) {
        Runnable addRunnable = () -> {
            Card card = cardDao.getItemById(id);
            card.setTitle(title);
            card.setUid(uid);
            card.setPassword(pw);
            card.setMessage(msg);
            card.setPin(pin);
            card.setCompany(company);
            cardDao.update(card);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // Card 아이템 삽입
    public void delete(int id) {
        Runnable addRunnable = () -> cardDao.delete(cardDao.getItemById(id));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}

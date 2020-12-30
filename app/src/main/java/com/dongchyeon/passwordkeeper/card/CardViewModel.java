package com.dongchyeon.passwordkeeper.card;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dongchyeon.passwordkeeper.database.entity.Card;
import com.dongchyeon.passwordkeeper.database.repository.CardRepository;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository repository;
    private LiveData<List<Card>> cards;

    public CardViewModel(Application application) {
        super(application);
        repository = new CardRepository(application);
        cards = repository.getCards();
    }

    public LiveData<List<Card>> getCards() { return cards; }

    public void insert(Card card) { repository.insert(card); }

    public void update(int id, String title, String uid, String pw, String msg, String pin, String company) {
        repository.update(id, title, uid, pw, msg, pin, company);
    }

    public void delete(int id) { repository.delete(id); }
}

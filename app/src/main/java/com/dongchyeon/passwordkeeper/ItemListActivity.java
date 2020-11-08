package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dongchyeon.passwordkeeper.card.CardAdapter;
import com.dongchyeon.passwordkeeper.card.CardEditActivity;
import com.dongchyeon.passwordkeeper.card.CardViewActivity;
import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CardDao;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.site.SiteAdapter;
import com.dongchyeon.passwordkeeper.site.SiteEditActivity;
import com.dongchyeon.passwordkeeper.site.SiteViewActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ItemListActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private SiteDao siteDao;
    private CardDao cardDao;

    private RecyclerView recyclerView;
    private SiteAdapter siteAdapter;
    private CardAdapter cardAdapter;

    private FloatingActionButton addButton;
    private String dataType;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        appDatabase = AppDatabase.getInstance(getApplicationContext());
        siteDao = appDatabase.siteDao();
        cardDao = appDatabase.cardDao();

        initUI();
    }

    private void initUI() {
        intent = getIntent();
        dataType = intent.getStringExtra("category");
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        switch(dataType) {
            case "사이트":
                siteAdapter = new SiteAdapter(appDatabase);
                recyclerView.setAdapter(siteAdapter);
                siteDao.getAll().observe(this, data -> siteAdapter.setItems(data));

                siteAdapter.setOnItemClickListener((holder, view, position) -> {
                    Intent intent = new Intent(getApplicationContext(), SiteViewActivity.class);

                    intent.putExtra("eid", siteAdapter.getItem(position).getEid());
                    intent.putExtra("title", siteAdapter.getItem(position).getTitle());
                    intent.putExtra("id", siteAdapter.getItem(position).getId());
                    intent.putExtra("pw", siteAdapter.getItem(position).getPassword());
                    intent.putExtra("url", siteAdapter.getItem(position).getUrl());

                    startActivity(intent);
                });
                break;
            case "카드":
                cardAdapter = new CardAdapter(appDatabase);
                recyclerView.setAdapter(cardAdapter);
                cardDao.getAll().observe(this, data -> cardAdapter.setItems(data));

                cardAdapter.setOnItemClickListener((holder, view, position) -> {
                    Intent intent = new Intent(getApplicationContext(), CardViewActivity.class);

                    intent.putExtra("eid", cardAdapter.getItem(position).getEid());
                    intent.putExtra("title", cardAdapter.getItem(position).getTitle());
                    intent.putExtra("id", cardAdapter.getItem(position).getId());
                    intent.putExtra("pw", cardAdapter.getItem(position).getPassword());
                    intent.putExtra("msg", cardAdapter.getItem(position).getMessage());
                    intent.putExtra("pin", cardAdapter.getItem(position).getPin());
                    intent.putExtra("company", cardAdapter.getItem(position).getCompany());

                    startActivity(intent);
                });
                break;
        }

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            switch(dataType) {
                case "사이트":
                    intent = new Intent(getApplicationContext(), SiteEditActivity.class);
                    startActivity(intent);
                    break;
                case "카드":
                    intent = new Intent(getApplicationContext(), CardEditActivity.class);
                    startActivity(intent);
                    break;
            }
        });
    }
}
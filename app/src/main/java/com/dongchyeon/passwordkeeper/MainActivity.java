package com.dongchyeon.passwordkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private SiteDao siteDao;

    private FloatingActionButton addButton;
    private FloatingActionButton addSiteButton;
    private TextView addSiteText;
    private RecyclerView recyclerView;
    private SiteAdapter adapter;

    private Animation fab_open;
    private Animation fab_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.getInstance(this);
        siteDao = appDatabase.siteDao();

        initUI();
    }

    private void initUI() {
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        addButton = findViewById(R.id.add_button);
        addSiteButton = findViewById(R.id.add_site_button);
        addSiteText = findViewById(R.id.add_site_text);
        recyclerView = findViewById(R.id.recycler_view);

        addSiteButton.startAnimation(fab_close);
        addSiteButton.setClickable(false);
        addSiteText.startAnimation(fab_close);

        addButton.setOnClickListener(view -> openAndCloseButton());
        addSiteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SitePostActivity.class);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new SiteAdapter(appDatabase);
        recyclerView.setAdapter(adapter);
        siteDao.getAll().observe(this, data -> adapter.setItems(data));
    }

    private void openAndCloseButton() {
        if (addSiteButton.isClickable()) {
            addSiteButton.startAnimation(fab_close);
            addSiteButton.setClickable(false);
            addSiteText.startAnimation(fab_close);
        } else {
            addSiteButton.startAnimation(fab_open);
            addSiteButton.setClickable(true);
            addSiteText.startAnimation(fab_open);
        }
    }
}
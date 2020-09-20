package com.dongchyeon.passwordkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SiteViewActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private SiteDao siteDao;

    private TextView titleView;
    private TextView idView;
    private TextView passwordView;
    private TextView urlView;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_view);

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        siteDao = appDatabase.siteDao();

        titleView = findViewById(R.id.title_view);
        idView = findViewById(R.id.id_view);
        passwordView = findViewById(R.id.password_view);
        urlView = findViewById(R.id.url_view);

        // 메인 액티비티로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", 0);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String password = intent.getStringExtra("password");
        String url = intent.getStringExtra("url");

        titleView.setText(title);
        idView.setText(id);
        passwordView.setText(password);
        urlView.setText(url);

        // 버튼 세팅
        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), SiteEditActivity.class);

            intent2.putExtra("eid", eid);
            intent2.putExtra("title", title);
            intent2.putExtra("id", id);
            intent2.putExtra("password", password);
            intent2.putExtra("url", url);

            startActivity(intent2);
            finish();
        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            delete(eid);
            finish();
        });
    }

    // Site 아이템 삽입
    private void delete(int eid) {
        Runnable addRunnable = () -> siteDao.delete(siteDao.getItemByEid(eid));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}
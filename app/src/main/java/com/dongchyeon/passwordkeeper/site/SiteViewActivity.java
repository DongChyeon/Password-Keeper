package com.dongchyeon.passwordkeeper.site;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.R;
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
    private TextView pwView;
    private TextView urlView;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    private LinearLayout titleLayout;
    private LinearLayout idLayout;
    private LinearLayout pwLayout;
    private LinearLayout urlLayout;

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
        pwView = findViewById(R.id.pw_view);
        urlView = findViewById(R.id.url_view);

        titleLayout = findViewById(R.id.title_layout);
        idLayout = findViewById(R.id.id_layout);
        pwLayout = findViewById(R.id.pw_layout);
        urlLayout = findViewById(R.id.url_layout);

        // 메인 액티비티로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", 0);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");
        String url = intent.getStringExtra("url");

        titleView.setText(title);
        idView.setText(id);
        pwView.setText(pw);
        urlView.setText(url);

        hideEmptyItem();

        // 버튼 세팅
        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), SiteEditActivity.class);

            intent2.putExtra("eid", eid);
            intent2.putExtra("title", title);
            intent2.putExtra("id", id);
            intent2.putExtra("pw", pw);
            intent2.putExtra("url", url);

            startActivity(intent2);
            finish();
        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            delete(eid);
            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Site 아이템 삽입
    private void delete(int eid) {
        Runnable addRunnable = () -> siteDao.delete(siteDao.getItemByEid(eid));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // 빈 항목 숨기기
    private void hideEmptyItem() {
        if (titleView.getText().toString().equals("")) {
            titleLayout.setVisibility(View.GONE);
        }
        if (idView.getText().toString().equals("")) {
            idLayout.setVisibility(View.GONE);
        }
        if (pwView.getText().toString().equals("")) {
            pwLayout.setVisibility(View.GONE);
        }
        if (urlView.getText().toString().equals("")) {
            urlLayout.setVisibility(View.GONE);
        }
    }
}
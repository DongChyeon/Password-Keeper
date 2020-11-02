package com.dongchyeon.passwordkeeper.site;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;
import android.widget.Toast;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.database.entity.Site;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SiteEditActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private SiteDao siteDao;
    private Site site;

    private EditText titleEdit;
    private EditText idEdit;
    private EditText passwordEdit;
    private EditText urlEdit;
    private FloatingActionButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_edit);

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        siteDao = appDatabase.siteDao();

        titleEdit = findViewById(R.id.title_edit);
        idEdit = findViewById(R.id.id_edit);
        passwordEdit = findViewById(R.id.password_edit);
        urlEdit = findViewById(R.id.url_edit);

        // SiteViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", -1);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String password = intent.getStringExtra("password");
        String url = intent.getStringExtra("url");

        titleEdit.setText(title);
        idEdit.setText(id);
        passwordEdit.setText(password);
        urlEdit.setText(url);

        // 버튼 세팅
        confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(view -> {
            if (eid != -1) {
                update(eid);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                site = new Site(titleEdit.getText().toString(), idEdit.getText().toString(), passwordEdit.getText().toString(), urlEdit.getText().toString());
                insert(site);
                Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Site 아이템 삽입
    private void insert(final Site site) {
        Runnable addRunnable = () -> siteDao.insert(site);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // Site 아이템 수정
    private void update(int eid) {
        Runnable addRunnable = () -> {
            site = siteDao.getItemByEid(eid);
            site.setTitle(titleEdit.getText().toString());
            site.setId(idEdit.getText().toString());
            site.setPassword(passwordEdit.getText().toString());
            site.setUrl(urlEdit.getText().toString());
            siteDao.update(site);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}
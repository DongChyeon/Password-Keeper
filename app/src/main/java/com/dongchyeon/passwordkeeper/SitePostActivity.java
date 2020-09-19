package com.dongchyeon.passwordkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.database.entity.Site;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SitePostActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private SiteDao siteDao;

    private EditText titleEdit;
    private EditText idEdit;
    private EditText passwordEdit;
    private EditText urlEdit;
    private FloatingActionButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_post);

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        siteDao = appDatabase.siteDao();

        titleEdit = findViewById(R.id.title_edit);
        idEdit = findViewById(R.id.id_edit);
        passwordEdit = findViewById(R.id.password_edit);
        urlEdit = findViewById(R.id.url_edit);

        confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(view -> {
            Site site = new Site(titleEdit.getText().toString(), idEdit.getText().toString(), passwordEdit.getText().toString(), urlEdit.getText().toString());
            insert(site);
            finish();
        });
    }

    private void insert(final Site site) {
        Runnable addRunnable = () -> siteDao.insert(site);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }   // Site 아이템 삽입
}
package com.dongchyeon.passwordkeeper.site;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.database.entity.Site;
import com.dongchyeon.passwordkeeper.databinding.ActivitySiteEditBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SiteEditActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private SiteDao siteDao;
    private Site site;

    private ActivitySiteEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiteEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        siteDao = appDatabase.siteDao();

        // SiteViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", -1);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");
        String url = intent.getStringExtra("url");

        binding.titleEdit.setText(title);
        binding.idEdit.setText(id);
        binding.pwEdit.setText(pw);
        binding.urlEdit.setText(url);

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener(view -> {
            if (eid != -1) {
                update(eid);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                site = new Site(binding.titleEdit.getText().toString(), binding.idEdit.getText().toString(), binding.pwEdit.getText().toString(), binding.urlEdit.getText().toString());
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
            site.setTitle(binding.titleEdit.getText().toString());
            site.setId(binding.idEdit.getText().toString());
            site.setPassword(binding.pwEdit.getText().toString());
            site.setUrl(binding.urlEdit.getText().toString());
            siteDao.update(site);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}
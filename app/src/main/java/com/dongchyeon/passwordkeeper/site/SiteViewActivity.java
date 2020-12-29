package com.dongchyeon.passwordkeeper.site;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.SiteDao;
import com.dongchyeon.passwordkeeper.databinding.ActivitySiteViewBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SiteViewActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private SiteDao siteDao;

    private ActivitySiteViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiteViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        siteDao = appDatabase.siteDao();

        // 메인 액티비티로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        String uid = intent.getStringExtra("uid");
        String pw = intent.getStringExtra("pw");
        String url = intent.getStringExtra("url");

        binding.titleView.setText(title);
        binding.idView.setText(uid);
        binding.pwView.setText(pw);
        binding.urlView.setText(url);

        hideEmptyItem();

        // 버튼 세팅
        binding.editBtn.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), SiteEditActivity.class);

            intent2.putExtra("id", id);
            intent2.putExtra("title", title);
            intent2.putExtra("uid", uid);
            intent2.putExtra("pw", pw);
            intent2.putExtra("url", url);

            startActivity(intent2);
            finish();
        });

        binding.deleteBtn.setOnClickListener(view -> {
            delete(id);
            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Site 아이템 삽입
    private void delete(int id) {
        Runnable addRunnable = () -> siteDao.delete(siteDao.getItemById(id));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // 빈 항목 숨기기
    private void hideEmptyItem() {
        if (binding.titleView.getText().toString().equals("")) {
            binding.titleLayout.setVisibility(View.GONE);
        }
        if (binding.idView.getText().toString().equals("")) {
            binding.idLayout.setVisibility(View.GONE);
        }
        if (binding.pwView.getText().toString().equals("")) {
            binding.pwLayout.setVisibility(View.GONE);
        }
        if (binding.urlView.getText().toString().equals("")) {
            binding.urlLayout.setVisibility(View.GONE);
        }
    }
}
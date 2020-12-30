package com.dongchyeon.passwordkeeper.site;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dongchyeon.passwordkeeper.databinding.ActivitySiteViewBinding;

public class SiteViewActivity extends AppCompatActivity {
    private ActivitySiteViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiteViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        SiteViewModel siteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(SiteViewModel.class);

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
            siteViewModel.delete(id);
            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        });
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
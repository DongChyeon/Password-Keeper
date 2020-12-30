package com.dongchyeon.passwordkeeper.site;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dongchyeon.passwordkeeper.database.entity.Site;
import com.dongchyeon.passwordkeeper.databinding.ActivitySiteEditBinding;

public class SiteEditActivity extends AppCompatActivity {
    private ActivitySiteEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySiteEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        SiteViewModel siteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(SiteViewModel.class);

        // SiteViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String uid = intent.getStringExtra("uid");
        String pw = intent.getStringExtra("pw");
        String url = intent.getStringExtra("url");

        binding.titleEdit.setText(title);
        binding.idEdit.setText(uid);
        binding.pwEdit.setText(pw);
        binding.urlEdit.setText(url);

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener(view -> {
            String titleText = binding.titleEdit.getText().toString();
            String uidText = binding.idEdit.getText().toString();
            String pwText = binding.pwEdit.getText().toString();
            String urlText = binding.urlEdit.getText().toString();

            if (id != -1) {
                siteViewModel.update(id, titleText, uidText, pwText, urlText);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Site site = new Site(titleText, uidText, pwText, urlText);
                siteViewModel.insert(site);
                Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
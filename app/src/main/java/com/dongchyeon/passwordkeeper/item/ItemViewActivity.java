package com.dongchyeon.passwordkeeper.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dongchyeon.passwordkeeper.databinding.ActivityItemViewBinding;

import java.util.Objects;

public class ItemViewActivity extends AppCompatActivity {
    private ActivityItemViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemViewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        ItemViewModel itemViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(ItemViewModel.class);

        // ItemListActivity 로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String category = intent.getStringExtra("category");
        String uid = intent.getStringExtra("uid");
        String pw = intent.getStringExtra("pw");
        String memo = intent.getStringExtra("memo");

        Objects.requireNonNull(getSupportActionBar()).setTitle(title);

        binding.titleView.setText(title);
        binding.categoryView.setText(category);
        binding.idView.setText(uid);
        binding.pwView.setText(pw);
        binding.memoView.setText(memo);

        hideEmptyItem();

        // 버튼 세팅
        binding.editBtn.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), ItemEditActivity.class);

            intent2.putExtra("id", id);
            intent2.putExtra("title", title);
            intent2.putExtra("category", category);
            intent2.putExtra("uid", uid);
            intent2.putExtra("pw", pw);
            intent2.putExtra("memo", memo);

            startActivity(intent2);
            finish();
        });

        binding.deleteBtn.setOnClickListener(view -> {
            itemViewModel.delete(id);
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
        if (binding.memoView.getText().toString().equals("")) {
            binding.memoLayout.setVisibility(View.GONE);
        }
    }
}
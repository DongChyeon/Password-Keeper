package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.category.CategoryAdapter;
import com.dongchyeon.passwordkeeper.database.repository.ItemRepository;
import com.dongchyeon.passwordkeeper.databinding.ActivityMainBinding;
import com.dongchyeon.passwordkeeper.item.ItemEditActivity;
import com.dongchyeon.passwordkeeper.item.ItemListActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ItemRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        Objects.requireNonNull(getSupportActionBar()).setTitle("메인 메뉴");
        setContentView(binding.getRoot());

        initUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initUI();
    }

    private void initUI() {
        // 뷰 페이저 세팅 (첫 칸은 전체 보기, 마지막 칸은 카테고리 추가
        CategoryAdapter adapter = new CategoryAdapter();
        repository = new ItemRepository(getApplication());
        if (repository.getAllCategories().size() == 0) {
            adapter.addItem("새 항목 추가");
            adapter.addItem("비밀번호 변경");
        } else {
            adapter.addItem("전체 보기");
            adapter.addItems(repository.getAllCategories());
            adapter.addItem("새 항목 추가");
            adapter.addItem("비밀번호 변경");
        }
        binding.viewPager.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view, position) -> {
            if (adapter.getItem(position).equals("새 항목 추가")) {
                startActivity(new Intent(getApplicationContext(), ItemEditActivity.class));
            } else if (adapter.getItem(position).equals("비밀번호 변경")) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("type", "reset");
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                intent.putExtra("category", adapter.getItem(position));
                startActivity(intent);
            }
        });
    }
}
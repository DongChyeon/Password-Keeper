package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.category.CategoryAdapter;
import com.dongchyeon.passwordkeeper.database.entity.Category;
import com.dongchyeon.passwordkeeper.database.repository.CategoryRepository;
import com.dongchyeon.passwordkeeper.databinding.ActivityMainBinding;
import com.dongchyeon.passwordkeeper.item.ItemListActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CategoryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        // 뷰 페이저 세팅 (첫 칸은 전체 보기, 마지막 칸은 카테고리 추가
        CategoryAdapter adapter = new CategoryAdapter();
        repository = new CategoryRepository(getApplication());
        adapter.addItem(new Category("전체 보기", "CATEGORY"));
        adapter.addItems(repository.getItems());
        adapter.addItem(new Category("카테고리 추가", "ADD"));
        binding.viewPager.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view, position) -> {
            if (adapter.getItem(position).getType().equals("ADD")) {
                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View dialogView = getLayoutInflater().inflate(R.layout.category_edit, null);
                    builder.setView(dialogView);
                    AlertDialog alertDialog = builder.create();

                    EditText categoryEdit = dialogView.findViewById(R.id.categoryEdit);
                    Button acceptBtn = dialogView.findViewById(R.id.acceptBtn);
                    acceptBtn.setOnClickListener(v -> {
                        repository.insert(new Category(categoryEdit.getText().toString(), "CATEGORY"));
                        adapter.addItem(new Category("전체 보기", "CATEGORY"));
                        adapter.addItems(repository.getItems());
                        adapter.addItem(new Category("카테고리 추가", "ADD"));
                        alertDialog.dismiss();
                    });
                    alertDialog.show();
                });
            } else {
                Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                intent.putExtra("category", adapter.getItem(position).getTitle());
                startActivity(intent);
            }
        });
    }
}
package com.dongchyeon.passwordkeeper.item;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dongchyeon.passwordkeeper.AES256Chiper;
import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.databinding.ActivityItemListBinding;

import java.util.Objects;

public class ItemListActivity extends AppCompatActivity {
    private ActivityItemListBinding binding;
    private String category;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_list);
        binding.setLifecycleOwner(this);

        initUI();
    }

    private void initUI() {
        // MainActivity 로부터 인텐트를 넘겨받음
        intent = getIntent();
        category = intent.getStringExtra("category");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Objects.requireNonNull(getSupportActionBar()).setTitle(category);

        ItemViewModel itemViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(ItemViewModel.class);
        ItemAdapter adapter = new ItemAdapter();
        if (!category.equals("전체 보기")) {
            itemViewModel.getItemsByCategory(category);
        }
        // 전체 보기가 아닐 경우 카테고리에 따른 아이템을 보여줌
        itemViewModel.getItems().observe(this, adapter::setItems);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getApplicationContext(), ItemViewActivity.class);

            intent.putExtra("id", adapter.getItem(position).getId());
            intent.putExtra("title", adapter.getItem(position).getTitle());
            intent.putExtra("category", adapter.getItem(position).getCategory());
            intent.putExtra("uid", adapter.getItem(position).getUid());
            // 비밀번호 복호화
            intent.putExtra("pw", AES256Chiper.AES_Decode(adapter.getItem(position).getPassword(), getApplicationContext().getResources().getString(R.string.SECRET_KEY)));
            intent.putExtra("memo", adapter.getItem(position).getMemo());

            startActivity(intent);
        });

        binding.addBtn.setOnClickListener(view -> {
            intent = new Intent(getApplicationContext(), ItemEditActivity.class);
            startActivity(intent);
        });
    }
}
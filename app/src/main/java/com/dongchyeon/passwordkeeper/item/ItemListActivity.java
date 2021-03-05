package com.dongchyeon.passwordkeeper.item;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.databinding.ActivityItemListBinding;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return true;
    }

    // 툴바에 삽입된 메뉴에 대해서 이벤트 처리
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup_btn:
                backupDatabase();
                return true;
            case R.id.restore_btn:
                restoreDatabase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUI() {
        intent = getIntent();
        category = intent.getStringExtra("category");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setSupportActionBar(binding.toolbar);

        ItemViewModel itemViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(ItemViewModel.class);
        ItemAdapter itemAdapter = new ItemAdapter();
        itemViewModel.getItemsByCategory(category);
        itemViewModel.getItems().observe(this, itemAdapter::setItems);
        binding.recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getApplicationContext(), ItemViewActivity.class);

            intent.putExtra("id", itemAdapter.getItem(position).getId());
            intent.putExtra("title", itemAdapter.getItem(position).getTitle());
            intent.putExtra("category", itemAdapter.getItem(position).getCategory());
            intent.putExtra("uid", itemAdapter.getItem(position).getUid());
            intent.putExtra("pw", itemAdapter.getItem(position).getPassword());
            intent.putExtra("memo", itemAdapter.getItem(position).getMemo());

            startActivity(intent);
        });

        binding.addBtn.setOnClickListener(view -> {
            intent = new Intent(getApplicationContext(), ItemEditActivity.class);
            startActivity(intent);
        });
    }

    private void backupDatabase() {

    }

    private void restoreDatabase() {

    }
}
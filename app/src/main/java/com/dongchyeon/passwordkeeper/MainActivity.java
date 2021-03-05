package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.category.CategoryAdapter;
import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.databinding.ActivityMainBinding;
import com.dongchyeon.passwordkeeper.item.ItemListActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CategoryAdapter adapter = new CategoryAdapter();
        binding.viewPager.setAdapter(adapter);
        AppDatabase.getInstance(getApplication()).itemDao().getCategories().observe(this, adapter::setItems);

        adapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
            intent.putExtra("category", adapter.getItem(position));
            startActivity(intent);
        });
    }
}
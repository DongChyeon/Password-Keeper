package com.dongchyeon.passwordkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.dongchyeon.passwordkeeper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CategoryAdapter adapter = new CategoryAdapter();
        binding.viewPager.setAdapter(adapter);
        adapter.addItem(new Category("사이트"));
        adapter.addItem(new Category("카드"));

        adapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
            intent.putExtra("category", adapter.getItem(position).getTitle());
            startActivity(intent);
        });
    }
}
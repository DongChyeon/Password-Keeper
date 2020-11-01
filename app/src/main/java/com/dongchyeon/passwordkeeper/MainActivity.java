package com.dongchyeon.passwordkeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);

        CategoryAdapter adapter = new CategoryAdapter();
        viewPager.setAdapter(adapter);
        adapter.addItem(new Category("사이트"));
        adapter.addItem(new Category("카드"));

        adapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
            intent.putExtra("category", adapter.getItem(position).getTitle());
            startActivity(intent);
        });
    }
}
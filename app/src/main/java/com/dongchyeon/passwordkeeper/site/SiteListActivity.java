package com.dongchyeon.passwordkeeper.site;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.databinding.ActivityItemListBinding;

public class SiteListActivity extends AppCompatActivity {
    private SiteViewModel siteViewModel;
    private ActivityItemListBinding binding;

    private SiteAdapter siteAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_item_list);
        binding.setLifecycleOwner(this);
        siteViewModel = new ViewModelProvider(this).get(SiteViewModel.class);
        siteAdapter = new SiteAdapter();
        siteViewModel.getSites().observe(this, sites -> siteAdapter.setItems(sites));

        initUI();
    }

    private void initUI() {
        intent = getIntent();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.recyclerView.setAdapter(siteAdapter);

        siteAdapter.setOnItemClickListener((holder, view, position) -> {
            Intent intent = new Intent(getApplicationContext(), SiteViewActivity.class);

            intent.putExtra("id", siteAdapter.getItem(position).getId());
            intent.putExtra("title", siteAdapter.getItem(position).getTitle());
            intent.putExtra("uid", siteAdapter.getItem(position).getUid());
            intent.putExtra("pw", siteAdapter.getItem(position).getPassword());
            intent.putExtra("url", siteAdapter.getItem(position).getUrl());

            startActivity(intent);
        });
        binding.addBtn.setOnClickListener(view -> {
            intent = new Intent(getApplicationContext(), SiteEditActivity.class);
            startActivity(intent);
        });
    }
}
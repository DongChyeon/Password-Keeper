package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dongchyeon.passwordkeeper.card.CardAdapter;
import com.dongchyeon.passwordkeeper.card.CardEditActivity;
import com.dongchyeon.passwordkeeper.card.CardViewActivity;
import com.dongchyeon.passwordkeeper.card.CardViewModel;
import com.dongchyeon.passwordkeeper.databinding.ActivityItemListBinding;
import com.dongchyeon.passwordkeeper.site.SiteAdapter;
import com.dongchyeon.passwordkeeper.site.SiteEditActivity;
import com.dongchyeon.passwordkeeper.site.SiteViewActivity;
import com.dongchyeon.passwordkeeper.site.SiteViewModel;

public class ItemListActivity extends AppCompatActivity {
    private ActivityItemListBinding binding;
    private String dataType;
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
        dataType = intent.getStringExtra("category");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        setSupportActionBar(binding.toolbar);

        switch(dataType) {
            case "사이트":
                SiteViewModel siteViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                        .get(SiteViewModel.class);
                SiteAdapter siteAdapter = new SiteAdapter();
                siteViewModel.getSites().observe(this, siteAdapter::setItems);
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
                break;
            case "카드":
                CardViewModel cardViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                        .get(CardViewModel.class);
                CardAdapter cardAdapter = new CardAdapter();
                cardViewModel.getCards().observe(this, cardAdapter::setItems);
                binding.recyclerView.setAdapter(cardAdapter);

                cardAdapter.setOnItemClickListener((holder, view, position) -> {
                    Intent intent = new Intent(getApplicationContext(), CardViewActivity.class);

                    intent.putExtra("id", cardAdapter.getItem(position).getId());
                    intent.putExtra("title", cardAdapter.getItem(position).getTitle());
                    intent.putExtra("uid", cardAdapter.getItem(position).getUid());
                    intent.putExtra("pw", cardAdapter.getItem(position).getPassword());
                    intent.putExtra("msg", cardAdapter.getItem(position).getMessage());
                    intent.putExtra("pin", cardAdapter.getItem(position).getPin());
                    intent.putExtra("company", cardAdapter.getItem(position).getCompany());

                    startActivity(intent);
                });
                break;
        }

        binding.addBtn.setOnClickListener(view -> {
            switch(dataType) {
                case "사이트":
                    intent = new Intent(getApplicationContext(), SiteEditActivity.class);
                    startActivity(intent);
                    break;
                case "카드":
                    intent = new Intent(getApplicationContext(), CardEditActivity.class);
                    startActivity(intent);
                    break;
            }
        });
    }

    private void backupDatabase() {

    }

    private void restoreDatabase() {

    }
}
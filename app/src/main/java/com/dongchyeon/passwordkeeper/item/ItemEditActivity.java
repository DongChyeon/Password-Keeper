package com.dongchyeon.passwordkeeper.item;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.database.entity.Item;
import com.dongchyeon.passwordkeeper.database.repository.CategoryRepository;
import com.dongchyeon.passwordkeeper.databinding.ActivityItemEditBinding;

public class ItemEditActivity extends AppCompatActivity {
    private ActivityItemEditBinding binding;
    private CategoryRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        ItemViewModel itemViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(ItemViewModel.class);
        repository = new CategoryRepository(getApplication());
        binding.categoryEdit.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, repository.getTitles()));

        // ItemViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        long id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String uid = intent.getStringExtra("uid");
        String pw = intent.getStringExtra("pw");
        String memo = intent.getStringExtra("memo");

        binding.titleEdit.setText(title);
        binding.categoryEdit.setSelection(0);
        binding.idEdit.setText(uid);
        binding.pwEdit.setText(pw);
        binding.memoEdit.setText(memo);

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener(view -> {
            String titleText = binding.titleEdit.getText().toString();
            String categoryText = binding.categoryEdit.getSelectedItem().toString();
            String uidText = binding.idEdit.getText().toString();
            String pwText = binding.pwEdit.getText().toString();
            String memoText = binding.memoEdit.getText().toString();

            if (id != -1) {
                itemViewModel.update(id, titleText, categoryText, uidText, pwText, memoText);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Item item = new Item(titleText, categoryText, uidText, pwText, memoText);
                itemViewModel.insert(item);
                Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }
}
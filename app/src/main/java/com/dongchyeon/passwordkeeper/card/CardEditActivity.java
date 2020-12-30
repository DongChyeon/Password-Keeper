package com.dongchyeon.passwordkeeper.card;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dongchyeon.passwordkeeper.database.entity.Card;
import com.dongchyeon.passwordkeeper.databinding.ActivityCardEditBinding;

public class CardEditActivity extends AppCompatActivity {
    private ActivityCardEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        CardViewModel cardViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(CardViewModel.class);

        // CardViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String uid = intent.getStringExtra("uid");
        String pw = intent.getStringExtra("pw");
        String msg = intent.getStringExtra("msg");
        String pin = intent.getStringExtra("pin");
        String company = intent.getStringExtra("company");

        binding.titleEdit.setText(title);
        binding.idEdit.setText(uid);
        binding.pwEdit.setText(pw);
        binding.msgEdit.setText(msg);
        binding.pinEdit.setText(pin);
        binding.companyEdit.setText(company);

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener(view -> {
            String titleText = binding.titleEdit.getText().toString();
            String uidText = binding.idEdit.getText().toString();
            String pwText = binding.pwEdit.getText().toString();
            String msgText = binding.msgEdit.getText().toString();
            String pinText = binding.pinText.getText().toString();
            String companyText = binding.companyText.getText().toString();

            if (id != -1) {
                cardViewModel.update(id, titleText, uidText, pwText, msgText, pinText, companyText);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Card card = new Card(titleText, uidText, pwText, msgText, pinText, companyText);
                cardViewModel.insert(card);
                Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
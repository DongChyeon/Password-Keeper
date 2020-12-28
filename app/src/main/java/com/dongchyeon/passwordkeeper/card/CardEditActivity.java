package com.dongchyeon.passwordkeeper.card;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CardDao;
import com.dongchyeon.passwordkeeper.database.entity.Card;
import com.dongchyeon.passwordkeeper.databinding.ActivityCardEditBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CardEditActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private CardDao cardDao;
    private Card card;
    
    private ActivityCardEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        cardDao = appDatabase.cardDao();

        // CardViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", -1);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");
        String msg = intent.getStringExtra("msg");
        String pin = intent.getStringExtra("pin");
        String company = intent.getStringExtra("company");

        binding.titleEdit.setText(title);
        binding.idEdit.setText(id);
        binding.pwEdit.setText(pw);
        binding.msgEdit.setText(msg);
        binding.pinEdit.setText(pin);
        binding.companyEdit.setText(company);

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener(view -> {
            if (eid != -1) {
                update(eid);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                card = new Card(binding.titleEdit.getText().toString(), binding.idEdit.getText().toString(), binding.pwEdit.getText().toString(), binding.msgEdit.getText().toString(), binding.pinEdit.getText().toString(), binding.companyEdit.getText().toString());
                insert(card);
                Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Card 아이템 삽입
    private void insert(final Card card) {
        Runnable addRunnable = () -> cardDao.insert(card);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // Card 아이템 수정
    private void update(int eid) {
        Runnable addRunnable = () -> {
            card = cardDao.getItemByEid(eid);
            card.setTitle(binding.titleEdit.getText().toString());
            card.setId(binding.idEdit.getText().toString());
            card.setPassword(binding.pwEdit.getText().toString());
            card.setMessage(binding.msgEdit.getText().toString());
            card.setPin(binding.pinEdit.getText().toString());
            card.setCompany(binding.companyEdit.getText().toString());
            cardDao.update(card);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}
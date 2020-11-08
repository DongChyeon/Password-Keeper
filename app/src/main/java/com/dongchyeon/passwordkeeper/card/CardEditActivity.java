package com.dongchyeon.passwordkeeper.card;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CardDao;
import com.dongchyeon.passwordkeeper.database.entity.Card;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CardEditActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private CardDao cardDao;
    private Card card;
    
    private EditText titleEdit;
    private EditText idEdit;
    private EditText pwEdit;
    private EditText msgEdit;
    private EditText pinEdit;
    private EditText companyEdit;
    private FloatingActionButton confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_edit);

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        cardDao = appDatabase.cardDao();
        
        titleEdit = findViewById(R.id.title_edit);
        idEdit = findViewById(R.id.id_edit);
        pwEdit = findViewById(R.id.pw_edit);
        msgEdit = findViewById(R.id.msg_edit);
        pinEdit = findViewById(R.id.pin_edit);
        companyEdit = findViewById(R.id.company_edit);

        // CardViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", -1);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");
        String msg = intent.getStringExtra("msg");
        String pin = intent.getStringExtra("pin");
        String company = intent.getStringExtra("company");

        titleEdit.setText(title);
        idEdit.setText(id);
        pwEdit.setText(pw);
        msgEdit.setText(msg);
        pinEdit.setText(pin);
        companyEdit.setText(company);

        // 버튼 세팅
        confirmButton = findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(view -> {
            if (eid != -1) {
                update(eid);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                card = new Card(titleEdit.getText().toString(), idEdit.getText().toString(), pwEdit.getText().toString(), msgEdit.getText().toString(), pinEdit.getText().toString(), companyEdit.getText().toString());
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
            card.setTitle(titleEdit.getText().toString());
            card.setId(idEdit.getText().toString());
            card.setPassword(pwEdit.getText().toString());
            card.setMessage(msgEdit.getText().toString());
            card.setPin(pinEdit.getText().toString());
            card.setCompany(companyEdit.getText().toString());
            cardDao.update(card);
        };
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}
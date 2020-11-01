package com.dongchyeon.passwordkeeper.card;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.card.CardEditActivity;
import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CardDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CardViewActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private CardDao cardDao;

    private TextView titleView;
    private TextView idView;
    private TextView passwordView;
    private TextView messageView;
    private TextView pinView;
    private TextView companyView;
    private FloatingActionButton editButton;
    private FloatingActionButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        cardDao = appDatabase.cardDao();

        titleView = findViewById(R.id.title_view);
        idView = findViewById(R.id.id_view);
        passwordView = findViewById(R.id.password_view);
        messageView = findViewById(R.id.message_view);
        pinView = findViewById(R.id.pin_view);
        companyView = findViewById(R.id.company_view);

        // 메인 액티비티로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", 0);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String password = intent.getStringExtra("password");
        String message = intent.getStringExtra("message");
        String pin = intent.getStringExtra("pin");
        String company = intent.getStringExtra("company");

        titleView.setText(title);
        idView.setText(id);
        passwordView.setText(password);
        messageView.setText(message);
        pinView.setText(pin);
        companyView.setText(company);

        // 버튼 세팅
        editButton = findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), CardEditActivity.class);

            intent2.putExtra("eid", eid);
            intent2.putExtra("title", title);
            intent2.putExtra("id", id);
            intent2.putExtra("password", password);
            intent2.putExtra("message", message);
            intent2.putExtra("pin", pin);
            intent2.putExtra("company", company);

            startActivity(intent2);
            finish();
        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            delete(eid);
            finish();
        });
    }

    // Card 아이템 삽입
    private void delete(int eid) {
        Runnable addRunnable = () -> cardDao.delete(cardDao.getItemByEid(eid));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }
}

package com.dongchyeon.passwordkeeper.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.database.AppDatabase;
import com.dongchyeon.passwordkeeper.database.dao.CardDao;
import com.dongchyeon.passwordkeeper.databinding.ActivityCardViewBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CardViewActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private CardDao cardDao;
    
    private ActivityCardViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        appDatabase = AppDatabase.getInstance(this);
        cardDao = appDatabase.cardDao();

        // 메인 액티비티로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int eid = intent.getIntExtra("eid", 0);
        String title = intent.getStringExtra("title");
        String id = intent.getStringExtra("id");
        String pw = intent.getStringExtra("pw");
        String msg = intent.getStringExtra("msg");
        String pin = intent.getStringExtra("pin");
        String company = intent.getStringExtra("company");

        binding.titleView.setText(title);
        binding.idView.setText(id);
        binding.pwView.setText(pw);
        binding.msgView.setText(msg);
        binding.pinView.setText(pin);
        binding.companyView.setText(company);

        hideEmptyItem();

        // 버튼 세팅
        binding.editBtn.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), CardEditActivity.class);

            intent2.putExtra("eid", eid);
            intent2.putExtra("title", title);
            intent2.putExtra("id", id);
            intent2.putExtra("pw", pw);
            intent2.putExtra("msg", msg);
            intent2.putExtra("pin", pin);
            intent2.putExtra("company", company);

            startActivity(intent2);
            finish();
        });

        binding.deleteBtn.setOnClickListener(view -> {
            delete(eid);
            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Card 아이템 삽입
    private void delete(int eid) {
        Runnable addRunnable = () -> cardDao.delete(cardDao.getItemByEid(eid));
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(addRunnable);
    }

    // 빈 항목 숨기기
    private void hideEmptyItem() {
        if (binding.titleView.getText().toString().equals("")) {
            binding.titleLayout.setVisibility(View.GONE);
        }
        if (binding.idView.getText().toString().equals("")) {
            binding.idLayout.setVisibility(View.GONE);
        }
        if (binding.pwView.getText().toString().equals("")) {
            binding.pwLayout.setVisibility(View.GONE);
        }
        if (binding.msgView.getText().toString().equals("")) {
            binding.msgLayout.setVisibility(View.GONE);
        }
        if (binding.pinView.getText().toString().equals("")) {
            binding.pinLayout.setVisibility(View.GONE);
        }
        if (binding.companyView.getText().toString().equals("")) {
            binding.companyLayout.setVisibility(View.GONE);
        }
    }
}

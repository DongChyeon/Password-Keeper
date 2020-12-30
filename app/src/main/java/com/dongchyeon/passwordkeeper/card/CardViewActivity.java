package com.dongchyeon.passwordkeeper.card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dongchyeon.passwordkeeper.databinding.ActivityCardViewBinding;

public class CardViewActivity extends AppCompatActivity {
    private ActivityCardViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        CardViewModel cardViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(CardViewModel.class);

        // 메인 액티비티로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        String uid = intent.getStringExtra("uid");
        String pw = intent.getStringExtra("pw");
        String msg = intent.getStringExtra("msg");
        String pin = intent.getStringExtra("pin");
        String company = intent.getStringExtra("company");

        binding.titleView.setText(title);
        binding.idView.setText(uid);
        binding.pwView.setText(pw);
        binding.msgView.setText(msg);
        binding.pinView.setText(pin);
        binding.companyView.setText(company);

        hideEmptyItem();

        // 버튼 세팅
        binding.editBtn.setOnClickListener(view -> {
            Intent intent2 = new Intent(getApplicationContext(), CardEditActivity.class);

            intent2.putExtra("id", id);
            intent2.putExtra("title", title);
            intent2.putExtra("uid", uid);
            intent2.putExtra("pw", pw);
            intent2.putExtra("msg", msg);
            intent2.putExtra("pin", pin);
            intent2.putExtra("company", company);

            startActivity(intent2);
            finish();
        });

        binding.deleteBtn.setOnClickListener(view -> {
            cardViewModel.delete(id);
            Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            finish();
        });
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

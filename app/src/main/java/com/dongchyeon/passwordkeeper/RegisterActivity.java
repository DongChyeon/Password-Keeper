package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dongchyeon.passwordkeeper.databinding.ActivityRegisterBinding;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 비밀번호 변경 시 재설정 페이지로도 사용됨
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type.equals("reset")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("비밀번호 재설정");
            binding.explainText.setText("새 비밀번호를 입력해주세요.");
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("초기 비밀번호 설정");
        }

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        binding.confirmBtn.setOnClickListener(view -> {
            if (binding.newPassword.getText().toString().equals(binding.confirmPassword.getText().toString())) {
                editor = prefs.edit();
                editor.putString("password", binding.newPassword.getText().toString());
                editor.putBoolean("isRegistered", true);
                editor.apply(); // 비밀번호 입력칸과 비밀번호 확인칸이 같을 시 비밀번호 설정 완료

                if (!type.equals("reset")) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 설정되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 재설정되었습니다.", Toast.LENGTH_SHORT).show();
                }
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "두 입력칸의 비밀번호가 일치하는지 확인하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
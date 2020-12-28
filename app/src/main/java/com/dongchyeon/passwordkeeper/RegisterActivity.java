package com.dongchyeon.passwordkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dongchyeon.passwordkeeper.databinding.ActivityLoginBinding;
import com.dongchyeon.passwordkeeper.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        binding.confirmBtn.setOnClickListener(view -> {
            if (binding.newPassword.getText().toString().equals(binding.confirmPassword.getText().toString())) {
                editor = prefs.edit();
                editor.putString("password", binding.newPassword.getText().toString());
                editor.putBoolean("isRegistered", true);
                editor.apply(); // 비밀번호 입력칸과 비밀번호 확인칸이 같을 시 비밀번호 설정 완료

                Intent intent = new Intent(getApplicationContext(), ItemListActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "두 입력칸의 비밀번호가 일치하는지 확인하세요.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
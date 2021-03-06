package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.dongchyeon.passwordkeeper.databinding.ActivityLoginBinding;

import java.util.Objects;
import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private SharedPreferences prefs;

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 비밀번호 변경 시 확인 페이지로도 사용됨
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if (type.equals("reset")) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("비밀번호 확인");
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("로그인");
        }

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        binding.loginBtn.setOnClickListener(view -> {
            if ((binding.inputPassword.getText().toString()).equals(prefs.getString("password", null))) {
                Toast.makeText(getApplicationContext(), "인증 성공", Toast.LENGTH_SHORT).show();
                if (type.equals("reset")) {
                    Intent intent2 = new Intent(getApplicationContext(), RegisterActivity.class);
                    intent2.putExtra("type", "reset");
                    startActivity(intent2);
                } else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
            }
        }); // prefs에 저장된 비밀번호(가입할 때 저장한 비밀번호)를 입력했을 시 로그인

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                if (errString != "비밀번호로 로그인 하시겠습니까?") {
                    Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "인증 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("지문 인식")
                .setNegativeButtonText("비밀번호로 로그인 하시겠습니까?")
                .build();
        biometricPrompt.authenticate(promptInfo);   // 지문 인식 창 띄우기
    }
}
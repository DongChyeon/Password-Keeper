package com.dongchyeon.passwordkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class LoginActivity extends AppCompatActivity {
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private SharedPreferences prefs;

    private EditText inputPassword;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        inputPassword = findViewById(R.id.intput_password);
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((inputPassword.getText().toString()).equals(prefs.getString("password", null))) {
                    Toast.makeText(getApplicationContext(), "인증 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                }
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
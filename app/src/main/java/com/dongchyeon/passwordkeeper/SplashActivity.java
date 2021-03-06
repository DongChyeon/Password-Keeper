package com.dongchyeon.passwordkeeper;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler(Looper.getMainLooper());
    private SharedPreferences prefs;
    private Boolean isRegistered;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        isRegistered = prefs.getBoolean("isRegistered", false);

        handler.postDelayed(() -> {
            if (isRegistered) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("type", "login");
                startActivity(intent);    // 비밀번호를 설정한 적이 있으면 로그인 페이지로 이동
            } else {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                intent.putExtra("type", "register");
                startActivity(intent); // 아니면 비밀번호 설정 페이지로 이동
            }
            finish();
        }, 1000);
    }
}
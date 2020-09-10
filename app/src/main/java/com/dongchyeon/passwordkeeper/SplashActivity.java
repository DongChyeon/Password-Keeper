package com.dongchyeon.passwordkeeper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler(Looper.getMainLooper());
    private SharedPreferences prefs;
    private Boolean isRegistered;

    @Override
    protected void onCreate(@Nullable Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        isRegistered = prefs.getBoolean("isRegistered", false);

        handler.postDelayed(new Runnable() {
            public void run() {
                if (isRegistered) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();   // 비밀번호를 설정한 적이 있으면 로그인 페이지로 이동
                } else {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                    finish();   // 아니면 비밀번호 설정 페이지로 이동
                }
            }
        }, 1000);
    }

}
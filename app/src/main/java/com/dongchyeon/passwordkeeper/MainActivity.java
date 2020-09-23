package com.dongchyeon.passwordkeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.dongchyeon.passwordkeeper.tab.SiteTab;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private SiteTab site;

    private FloatingActionButton addButton;
    private FloatingActionButton addSiteButton;
    private FloatingActionButton addCardButton;
    private FloatingActionButton addEtcButton;
    private TextView addSiteText;
    private TextView addCardText;
    private TextView addEtcText;

    BottomNavigationView bottomNavigationView;

    private Animation fab_open;
    private Animation fab_close;

    private boolean openFlag;   // 메뉴 열고 닫기 컨트롤 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        // 버튼 세팅
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        addButton = findViewById(R.id.add_button);
        addSiteButton = findViewById(R.id.add_site_button);
        addSiteText = findViewById(R.id.add_site_text);
        addCardButton = findViewById(R.id.add_card_button);
        addCardText = findViewById(R.id.add_card_text);
        addEtcButton = findViewById(R.id.add_etc_button);
        addEtcText = findViewById(R.id.add_etc_text);

        openFlag = false;
        openAndCloseButton();

        addButton.setOnClickListener(view -> openAndCloseButton());
        addSiteButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SiteEditActivity.class);
            startActivity(intent);
        });

        // 하단 네비게이션 세팅
        site = new SiteTab();

        //getSupportFragmentManager().beginTransaction().replace(R.id.container, site).commit();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.add_site:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, site).commit();
                            return true;
                    }
                    return false;
                });
    }

    private void openAndCloseButton() {
        if (openFlag) {
            addSiteButton.startAnimation(fab_open);
            addSiteButton.setClickable(true);
            addSiteText.startAnimation(fab_open);
            addCardButton.startAnimation(fab_open);
            addCardButton.setClickable(true);
            addCardText.startAnimation(fab_open);
            addEtcButton.startAnimation(fab_open);
            addEtcButton.setClickable(true);
            addEtcText.startAnimation(fab_open);
            openFlag = false;
        } else {
            addSiteButton.startAnimation(fab_close);
            addSiteButton.setClickable(false);
            addSiteText.startAnimation(fab_close);
            addCardButton.startAnimation(fab_close);
            addCardButton.setClickable(false);
            addCardText.startAnimation(fab_close);
            addEtcButton.startAnimation(fab_close);
            addEtcButton.setClickable(false);
            addEtcText.startAnimation(fab_close);
            openFlag = true;
        }
    }
}
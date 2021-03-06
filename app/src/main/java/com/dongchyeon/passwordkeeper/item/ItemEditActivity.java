package com.dongchyeon.passwordkeeper.item;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.dongchyeon.passwordkeeper.AES256Chiper;
import com.dongchyeon.passwordkeeper.R;
import com.dongchyeon.passwordkeeper.database.entity.Item;
import com.dongchyeon.passwordkeeper.databinding.ActivityItemEditBinding;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ItemEditActivity extends AppCompatActivity {
    private ActivityItemEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initUI();
    }

    private void initUI() {
        ItemViewModel itemViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(ItemViewModel.class);

        // ItemViewActivity로부터 인텐트를 넘겨받음
        Intent intent = getIntent();

        int id = intent.getIntExtra("id", -1);
        String title = intent.getStringExtra("title");
        String category = intent.getStringExtra("category");
        String uid = intent.getStringExtra("uid");
        String pw = intent.getStringExtra("pw");
        String memo = intent.getStringExtra("memo");

        binding.titleEdit.setText(title);
        binding.categoryEdit.setText(category);
        binding.idEdit.setText(uid);
        binding.pwEdit.setText(pw);
        binding.memoEdit.setText(memo);

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener(view -> {
            String titleText = binding.titleEdit.getText().toString();
            String categoryText = binding.categoryEdit.getText().toString();
            String uidText = binding.idEdit.getText().toString();
            // 비밀번호 암호화
            String pwText = null;
            try {
                pwText = AES256Chiper.AES_Encode(binding.pwEdit.getText().toString(), getApplicationContext().getResources().getString(R.string.SECRET_KEY));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
            String memoText = binding.memoEdit.getText().toString();

            if (categoryText.equals("전체 보기") || categoryText.equals("새 항목 추가")) {
                Toast.makeText(getApplicationContext(), "불가능한 카테고리명입니다.", Toast.LENGTH_SHORT).show();
            } else if (titleText.equals("")) {
                Toast.makeText(getApplicationContext(), "제목은 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show();
            } else if (categoryText.equals("")) {
                Toast.makeText(getApplicationContext(), "카테고리는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show();
            } else if (uidText.equals("")) {
                Toast.makeText(getApplicationContext(), "아이디는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show();
            } else if (pwText.equals("")) {
                Toast.makeText(getApplicationContext(), "비밀번호는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show();
            } else if (id != -1) {
                itemViewModel.update(id, titleText, categoryText, uidText, pwText, memoText);    // 이미 있는 아이템일 경우 업데이트
                Toast.makeText(getApplicationContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Item item = new Item(titleText, categoryText, uidText, pwText, memoText);
                itemViewModel.insert(item);
                Toast.makeText(getApplicationContext(), "추가되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
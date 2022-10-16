package com.dongchyeon.passwordkeeper.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dongchyeon.passwordkeeper.databinding.ActivityRegisterBinding
import com.dongchyeon.passwordkeeper.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    fun init() {
        // 비밀번호 변경 시 재설정 페이지로도 사용됨
        val intent = intent
        val type = intent.getStringExtra("type")
        if (type == "reset") {
            Objects.requireNonNull(supportActionBar)?.setTitle("비밀번호 재설정")
            binding.explainText.text = "새 비밀번호를 입력해주세요."
        } else {
            Objects.requireNonNull(supportActionBar)?.setTitle("초기 비밀번호 설정")
        }

        binding.confirmBtn.setOnClickListener {
            if (binding.newPassword.text.toString() == binding.confirmPassword.text.toString()) {
                viewModel.prefPassword = binding.newPassword.text.toString()
                viewModel.prefIsRegistered = true

                if (type != "reset") {
                    Toast.makeText(applicationContext, "비밀번호가 설정되었습니다.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "비밀번호가 재설정되었습니다.", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(applicationContext, "두 입력칸의 비밀번호가 일치하는지 확인하세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
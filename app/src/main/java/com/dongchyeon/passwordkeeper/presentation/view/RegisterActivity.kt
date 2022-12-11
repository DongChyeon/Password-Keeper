package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.ActivityRegisterBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseActivity
import com.dongchyeon.passwordkeeper.presentation.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    fun init() {
        // 비밀번호 변경 시 재설정 페이지로도 사용됨
        val intent = intent
        val type = intent.getStringExtra("type")
        if (type == "reset") {
            Objects.requireNonNull(supportActionBar)?.title = "비밀번호 재설정"
            binding.explainText.text = "새 비밀번호를 입력해주세요."
        } else {
            Objects.requireNonNull(supportActionBar)?.title = "초기 비밀번호 설정"
        }

        viewModel.isPasswordSet.observe(this) { success ->
            if (success) finish()
        }

        viewModel.toastMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }

        binding.confirmBtn.setOnClickListener {
            viewModel.setPassword(
                binding.newPassword.text.toString(),
                binding.confirmPassword.text.toString()
            )
        }
    }
}
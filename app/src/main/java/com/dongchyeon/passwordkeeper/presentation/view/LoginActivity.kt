package com.dongchyeon.passwordkeeper.presentation.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.ActivityLoginBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseActivity
import com.dongchyeon.passwordkeeper.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.Executor

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        // 비밀번호 변경 시 확인 페이지로도 사용됨
        val intent = intent
        val type = intent.getStringExtra("type")
        if (type == "reset") {
            Objects.requireNonNull(supportActionBar)?.title = "비밀번호 확인"
        } else {
            Objects.requireNonNull(supportActionBar)?.title = "로그인"
        }

        // 첫 실행이면은 RegisterActivity 로 전환
        viewModel.isRegistered.observe(this) { isRegistered ->
            if (!isRegistered) {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                intent.putExtra("type", "register")
                startActivity(intent)
                finish()
            }
        }

        viewModel.isLoggedIn.observe(this) { isLoggedIn ->
            if (isLoggedIn) {
                if (type == "reset") {
                    val intent = Intent(applicationContext, RegisterActivity::class.java)
                    intent.putExtra("type", "reset")
                    startActivity(intent)
                    finish()
                } else {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
            }
        }

        viewModel.toastMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }

        // 지문 인식 정보 세팅
        executor = ContextCompat.getMainExecutor(this@LoginActivity)
        biometricPrompt = BiometricPrompt(
            this@LoginActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (errString !== "비밀번호로 로그인 하기") {
                        Toast.makeText(applicationContext, errString, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "인증 성공", Toast.LENGTH_SHORT).show()
                    if (type == "reset") {
                        val intent2 = Intent(applicationContext, RegisterActivity::class.java)
                        intent2.putExtra("type", "reset")
                        startActivity(intent2)
                    } else {
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }
                    finish()
                }
            })

        promptInfo = PromptInfo.Builder()
            .setTitle("지문 인식")
            .setNegativeButtonText("비밀번호로 로그인 하기")
            .build()
        biometricPrompt.authenticate(promptInfo) // 지문 인식 창 띄우기

        // prefs에 저장된 비밀번호(가입할 때 저장한 비밀번호)를 입력했을 시 로그인
        binding.loginBtn.setOnClickListener {
            viewModel.login(binding.inputPassword.text.toString())
        }
    }
}
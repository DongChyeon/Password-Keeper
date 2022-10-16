package com.dongchyeon.passwordkeeper.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import com.dongchyeon.passwordkeeper.databinding.ActivityLoginBinding
import com.dongchyeon.passwordkeeper.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.Executor

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 비밀번호 변경 시 확인 페이지로도 사용됨
        val intent = intent
        val type = intent.getStringExtra("type")
        if (type == "reset") {
            Objects.requireNonNull(supportActionBar)?.title = "비밀번호 확인"
        } else {
            Objects.requireNonNull(supportActionBar)?.title = "로그인"
        }

        // 첫 실행이면은 RegisterActivity 로 전환
        if (!viewModel.prefIsRegistered) {
            val intent = Intent(applicationContext, RegisterActivity::class.java)
            intent.putExtra("type", "register")
            startActivity(intent)
            finish()
        } else {
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
        }

        // prefs에 저장된 비밀번호(가입할 때 저장한 비밀번호)를 입력했을 시 로그인
        binding.loginBtn.setOnClickListener {
            if (binding.inputPassword.text.toString() == viewModel.prefPassword) {
                Toast.makeText(applicationContext, "인증 성공", Toast.LENGTH_SHORT).show()
                if (type == "reset") {
                    val intent2 = Intent(applicationContext, RegisterActivity::class.java)
                    intent2.putExtra("type", "reset")
                    startActivity(intent2)
                } else {
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                }
                finish()
            } else {
                Toast.makeText(applicationContext, "비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
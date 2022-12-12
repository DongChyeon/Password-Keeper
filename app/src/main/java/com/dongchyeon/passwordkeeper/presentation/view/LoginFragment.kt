package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.FragmentLoginBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseFragment
import com.dongchyeon.passwordkeeper.presentation.viewmodel.LoginViewModel
import com.dongchyeon.passwordkeeper.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executor

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo
    private val args: LoginFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            viewModel = loginViewModel
        }
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        // 비밀번호 변경 시 확인 페이지로도 사용됨
        val type = args.type

        // 첫 실행이면은 RegisterActivity 로 전환
        loginViewModel.isRegistered.observe(requireActivity(), EventObserver { isRegistered ->
            if (!isAdded) return@EventObserver
            if (!isRegistered) {
                val action =
                    LoginFragmentDirections
                        .actionFragmentLoginToFragmentRegister()
                navController.navigate(action)
            } else {
                // 지문 인식 정보 세팅
                executor = ContextCompat.getMainExecutor(requireContext())
                biometricPrompt = BiometricPrompt(
                    this@LoginFragment,
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            if (errString !== "비밀번호로 로그인 하기") {
                                Toast.makeText(requireContext(), errString, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            Toast.makeText(requireContext(), "인증 성공", Toast.LENGTH_SHORT).show()
                            if (type == "reset") {
                                val action =
                                    LoginFragmentDirections
                                        .actionFragmentLoginToFragmentRegister("reset")
                                navController.navigate(action)
                            } else {
                                val action =
                                    LoginFragmentDirections
                                        .actionFragmentLoginToFragmentCategory()
                                navController.navigate(action)
                            }
                        }
                    })

                promptInfo = PromptInfo.Builder()
                    .setTitle("지문 인식")
                    .setNegativeButtonText("비밀번호로 로그인 하기")
                    .build()
                biometricPrompt.authenticate(promptInfo) // 지문 인식 창 띄우기
            }
        })

        loginViewModel.isLoggedIn.observe(requireActivity(), EventObserver { isLoggedIn ->
            if (!isAdded) return@EventObserver
            if (isLoggedIn) {
                if (type == "reset") {
                    val action =
                        LoginFragmentDirections
                            .actionFragmentLoginToFragmentRegister("reset")
                    navController.navigate(action)
                } else {
                    val action =
                        LoginFragmentDirections
                            .actionFragmentLoginToFragmentCategory()
                    navController.navigate(action)
                }
            }
        })

        loginViewModel.toastMessage.observe(requireActivity()) {
            if (!isAdded) return@observe
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        // prefs에 저장된 비밀번호(가입할 때 저장한 비밀번호)를 입력했을 시 로그인
        binding.loginBtn.setOnClickListener {
            loginViewModel.login(binding.inputPassword.text.toString())
        }
    }
}
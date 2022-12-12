package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.FragmentRegisterBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseFragment
import com.dongchyeon.passwordkeeper.presentation.viewmodel.RegisterViewModel
import com.dongchyeon.passwordkeeper.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    private val registerViewModel: RegisterViewModel by viewModels()
    private val args: RegisterFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            viewModel = registerViewModel
        }
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        // 비밀번호 변경 시 재설정 페이지로도 사용됨
        val type = args.type
        if (type == "reset") binding.explainText.text = "새 비밀번호를 입력해주세요."

        registerViewModel.isPasswordSet.observe(requireActivity(), EventObserver { success ->
            if (!isAdded) return@EventObserver
            if (success) {
                if (type == "reset") {
                    val action = RegisterFragmentDirections
                        .actionFragmentResetToFragmentCategory()
                    navController.navigate(action)
                } else {
                    val action =
                        RegisterFragmentDirections
                            .actionFragmentRegisterToFragmentCategory()
                    navController.navigate(action)
                }
            }
        })

        registerViewModel.toastMessage.observe(requireActivity()) {
            if (!isAdded) return@observe
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        binding.confirmBtn.setOnClickListener {
            registerViewModel.setPassword(
                binding.newPassword.text.toString(),
                binding.confirmPassword.text.toString()
            )
        }
    }
}
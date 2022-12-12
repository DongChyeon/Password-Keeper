package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.FragmentMemoEditBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseFragment
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MemoEditViewModel
import com.dongchyeon.passwordkeeper.util.AES256Chiper
import com.dongchyeon.passwordkeeper.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoEditFragment : BaseFragment<FragmentMemoEditBinding>(R.layout.fragment_memo_edit) {
    private val memoEditViewModel: MemoEditViewModel by viewModels()
    private val args: MemoEditFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            viewModel = memoEditViewModel
        }
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        val type = args.type
        val memoId = args.memoId
        if (type == "update") {
            memoEditViewModel.loadMemo(memoId)
            memoEditViewModel.memo.observe(requireActivity()) { memo ->
                if (!isAdded) return@observe
                binding.titleEdit.setText(memo.title)
                binding.categoryEdit.setText(memo.category)
                binding.idEdit.setText(memo.uid)
                binding.pwEdit.setText(
                    AES256Chiper.AES_Decode(
                        memo.password,
                        requireContext().resources.getString(R.string.SECRET_KEY)
                    )
                )
                binding.memoEdit.setText(memo.memo)
            }
        }

        memoEditViewModel.insertedId.observe(requireActivity(), EventObserver { id ->
            if (!isAdded) return@EventObserver
            if (id != -1L) {
                val action = MemoEditFragmentDirections
                    .actionFragmentMemoEditToFragmentMemoDetail(id)
                navController.navigate(action)
            }
        })

        memoEditViewModel.isUpdated.observe(requireActivity(), EventObserver { success ->
            if (!isAdded) return@EventObserver
            if (success) {
                val action = MemoEditFragmentDirections
                    .actionFragmentMemoEditToFragmentMemoDetail(memoId)
                navController.navigate(action)
            }
        })

        memoEditViewModel.toastMessage.observe(requireActivity()) {
            if (!isAdded) return@observe
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener {
            val titleText = binding.titleEdit.text.toString()
            val categoryText = binding.categoryEdit.text.toString()
            val uidText = binding.idEdit.text.toString()
            // 비밀번호 암호화
            val pwText = AES256Chiper.AES_Encode(
                binding.pwEdit.text.toString(),
                requireContext().resources.getString(R.string.SECRET_KEY)
            )
            val memoText = binding.memoEdit.text.toString()

            if (type == "update") {
                memoEditViewModel.update(memoId, titleText, categoryText, uidText, pwText, memoText)
            } else {
                memoEditViewModel.insert(titleText, categoryText, uidText, pwText, memoText)
            }
        }
    }
}
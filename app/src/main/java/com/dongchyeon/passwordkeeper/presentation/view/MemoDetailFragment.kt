package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.FragmentMemoDetailBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseFragment
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MemoDetailViewModel
import com.dongchyeon.passwordkeeper.util.AES256Chiper
import com.dongchyeon.passwordkeeper.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoDetailFragment : BaseFragment<FragmentMemoDetailBinding>(R.layout.fragment_memo_detail) {
    private val memoDetailViewModel: MemoDetailViewModel by viewModels()
    private val args: MemoDetailFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            viewModel = memoDetailViewModel
        }
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        val memoId = args.memoId
        memoDetailViewModel.loadMemo(memoId)

        memoDetailViewModel.isDeleted.observe(requireActivity(), EventObserver { isDeleted ->
            if (!isAdded) return@EventObserver
            if (isDeleted) {
                val action =
                    MemoDetailFragmentDirections
                        .actionFragmentMemoDetailToFragmentCategory()
                navController.navigate(action)
            }
        })

        memoDetailViewModel.toastMessage.observe(requireActivity()) {
            if (!isAdded) return@observe
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        memoDetailViewModel.memo.observe(requireActivity()) { memo ->
            if (!isAdded) return@observe
            binding.titleView.text = memo.title
            binding.categoryView.text = memo.category
            binding.idView.text = memo.uid
            binding.pwView.text = AES256Chiper.AES_Decode(
                memo.password,
                requireContext().resources.getString(R.string.SECRET_KEY)
            )
            binding.memoView.text = memo.memo
            hideEmptyItem()
            binding.pwLayout.setOnClickListener {
                binding.pwText.text = "비밀번호"
                binding.pwView.inputType = InputType.TYPE_CLASS_TEXT
            }

            // 버튼 세팅
            binding.editBtn.setOnClickListener {
                val action =
                    MemoDetailFragmentDirections
                        .actionFragmentMemoDetailToFragmentMemoEdit("update", memoId)
                navController.navigate(action)
            }
            binding.deleteBtn.setOnClickListener {
                memoDetailViewModel.delete(memo)
            }
        }
    }

    // 빈 항목 숨기기
    private fun hideEmptyItem() {
        if (binding.titleView.text.toString() == "") {
            binding.titleLayout.visibility = View.GONE
        }
        if (binding.idView.text.toString() == "") {
            binding.idLayout.visibility = View.GONE
        }
        if (binding.pwView.text.toString() == "") {
            binding.pwLayout.visibility = View.GONE
        }
        if (binding.memoView.text.toString() == "") {
            binding.memoLayout.visibility = View.GONE
        }
    }
}
package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.FragmentMemoListBinding
import com.dongchyeon.passwordkeeper.presentation.adapter.MemoAdapter
import com.dongchyeon.passwordkeeper.presentation.base.BaseFragment
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MemoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemoListFragment : BaseFragment<FragmentMemoListBinding>(R.layout.fragment_memo_list) {
    private val memoListViewModel: MemoListViewModel by viewModels()
    private val args: MemoListFragmentArgs by navArgs()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            viewModel = memoListViewModel
        }
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        // MainActivity 로부터 인텐트를 넘겨받음
        val category = args.category
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = MemoAdapter()
        // 전체 보기가 아닐 경우 카테고리에 따른 아이템을 보여줌
        if (category == "전체 보기") {
            memoListViewModel.loadAllMemos()
        } else {
            memoListViewModel.loadMemosByCategory(category)
        }
        binding.recyclerView.adapter = adapter

        memoListViewModel.memos.observe(requireActivity()) { memos ->
            if (!isAdded) return@observe
            memos.let { adapter.submitList(memos) }
        }

        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val action =
                    MemoListFragmentDirections
                        .actionFragmentMemoListToFragmentMemoDetail(adapter.getMemo(position).id)
                navController.navigate(action)
            }
        })

        binding.addBtn.setOnClickListener {
            val action =
                MemoListFragmentDirections
                    .actionFragmentMemoListToFragmentMemoEdit("insert")
            navController.navigate(action)
        }
    }
}
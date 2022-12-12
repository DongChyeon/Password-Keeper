package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.FragmentCategoryBinding
import com.dongchyeon.passwordkeeper.presentation.adapter.CategoryAdapter
import com.dongchyeon.passwordkeeper.presentation.base.BaseFragment
import com.dongchyeon.passwordkeeper.presentation.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(R.layout.fragment_category) {
    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind {
            viewModel = categoryViewModel
        }
        navController = Navigation.findNavController(view)

        init()
    }

    private fun init() {
        // 뷰 페이저 세팅 (첫 칸은 전체 보기, 마지막 칸은 카테고리 추가
        val adapter = CategoryAdapter()
        binding.viewPager.adapter = adapter

        categoryViewModel.loadAllCategories()
        categoryViewModel.categories.observe(requireActivity()) { categories ->
            if (!isAdded) return@observe
            categories.let { adapter.submitList(categories) }
            binding.statusMsg.text =
                if (categories.isNotEmpty()) categoryViewModel.message1 else categoryViewModel.message2
        }

        adapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(
                view: View,
                position: Int
            ) {
                val action =
                    CategoryFragmentDirections
                        .actionFragmentCategoryToFragmentMemoList(adapter.getCategory(position))
                navController.navigate(action)
            }
        })

        binding.changePwBtn.setOnClickListener {
            val action =
                CategoryFragmentDirections
                    .actionFragmentCategoryToFragmentLogin("reset")
            navController.navigate(action)
        }

        binding.addBtn.setOnClickListener {
            val action =
                CategoryFragmentDirections
                    .actionFragmentCategoryToFragmentMemoEdit("insert")
            navController.navigate(action)
        }
    }
}
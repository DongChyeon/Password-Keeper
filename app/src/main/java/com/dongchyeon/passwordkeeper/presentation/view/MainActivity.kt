package com.dongchyeon.passwordkeeper.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.ActivityMainBinding
import com.dongchyeon.passwordkeeper.presentation.adapter.CategoryAdapter
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            lifecycleOwner = this@MainActivity
            viewModel = mainViewModel
        }

        init()
    }

    private fun init() {
        Objects.requireNonNull(supportActionBar)?.title = "메인 메뉴"

        // 뷰 페이저 세팅 (첫 칸은 전체 보기, 마지막 칸은 카테고리 추가
        val adapter = CategoryAdapter()
        binding.viewPager.adapter = adapter

        mainViewModel.categories.observe(this) { categories ->
            categories.let { adapter.submitList(categories) }
            binding.statusMsg.text =
                if (categories.isNotEmpty()) mainViewModel.message1 else mainViewModel.message2
        }

        adapter.setOnItemClickListener(object : CategoryAdapter.OnItemClickListener {
            override fun onItemClick(
                view: View,
                position: Int
            ) {
                val intent = Intent(applicationContext, MemoListActivity::class.java)
                intent.putExtra("category", adapter.getCategory(position))
                startActivity(intent)
            }
        })

        binding.changePwBtn.setOnClickListener {
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.putExtra("type", "reset")
            startActivity(intent)
        }

        binding.addBtn.setOnClickListener {
            val intent = Intent(applicationContext, MemoEditActivity::class.java)
            intent.putExtra("type", "insert")
            startActivity(intent)
        }
    }
}
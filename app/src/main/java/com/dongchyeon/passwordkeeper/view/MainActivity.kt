package com.dongchyeon.passwordkeeper.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dongchyeon.passwordkeeper.adapter.CategoryAdapter
import com.dongchyeon.passwordkeeper.databinding.ActivityMainBinding
import com.dongchyeon.passwordkeeper.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        Objects.requireNonNull(supportActionBar)?.title = "메인 메뉴"
        setContentView(view)

        init()
    }

    private fun init() {
        // 뷰 페이저 세팅 (첫 칸은 전체 보기, 마지막 칸은 카테고리 추가
        val adapter = CategoryAdapter()
        binding.viewPager.adapter = adapter

        viewModel.categories.observe(this) { categories ->
            categories.let { adapter.submitList(categories) }
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

        if (adapter.itemCount == 0) binding.textView.visibility = View.GONE

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
package com.dongchyeon.passwordkeeper.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.adapter.MemoAdapter
import com.dongchyeon.passwordkeeper.databinding.ActivityMemoListBinding
import com.dongchyeon.passwordkeeper.viewmodel.MemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MemoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoListBinding
    private val viewModel: MemoViewModel by viewModels()
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_list)
        binding.lifecycleOwner = this

        initUI()
    }

    private fun initUI() {
        // MainActivity 로부터 인텐트를 넘겨받음
        intent = intent
        category = intent.getStringExtra("category")
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        Objects.requireNonNull(supportActionBar)?.title = category

        val adapter = MemoAdapter()
        // 전체 보기가 아닐 경우 카테고리에 따른 아이템을 보여줌
        if (category == "전체 보기") {
            viewModel.loadAllMemos()
        } else {
            viewModel.loadMemosByCategory(category!!)
        }
        binding.recyclerView.adapter = adapter

        viewModel.memos.observe(this) { memos ->
            memos.let { adapter.submitList(memos) }
        }

        adapter.setOnItemClickListener(object : MemoAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(applicationContext, MemoViewActivity::class.java)
                intent.putExtra("memo", adapter.getMemo(position))
                startActivity(intent)
            }
        })

        binding.addBtn.setOnClickListener {
            startActivity(Intent(applicationContext, MemoEditActivity::class.java))
        }
    }
}
package com.dongchyeon.passwordkeeper.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.ActivityMemoListBinding
import com.dongchyeon.passwordkeeper.presentation.adapter.MemoAdapter
import com.dongchyeon.passwordkeeper.presentation.base.BaseActivity
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MemoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MemoListActivity : BaseActivity<ActivityMemoListBinding>(R.layout.activity_memo_list) {
    private val viewModel: MemoListViewModel by viewModels()
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
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
                val intent = Intent(applicationContext, MemoDetailActivity::class.java)
                intent.putExtra("memo", adapter.getMemo(position).id)
                startActivity(intent)
            }
        })

        binding.addBtn.setOnClickListener {
            val intent = Intent(applicationContext, MemoEditActivity::class.java)
            intent.putExtra("type", "insert")
            startActivity(intent)
        }
    }
}
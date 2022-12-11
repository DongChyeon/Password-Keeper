package com.dongchyeon.passwordkeeper.presentation.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.data.model.Memo
import com.dongchyeon.passwordkeeper.databinding.ActivityMemoViewBinding
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MemoViewModel
import com.dongchyeon.passwordkeeper.util.AES256Chiper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MemoViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoViewBinding
    private lateinit var memo: Memo
    private val viewModel: MemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memo_view)

        init()
    }

    private fun init() {
        // MemoListActivity 로부터 인텐트를 넘겨받음
        memo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("memo", Memo::class.java)!!
        } else {
            intent.getParcelableExtra<Memo>("memo")!!
        }
        Objects.requireNonNull(supportActionBar)?.title = memo.title

        binding.titleView.text = memo.title
        binding.categoryView.text = memo.category
        binding.idView.text = memo.uid
        binding.pwView.text = AES256Chiper.AES_Decode(
            memo.password,
            applicationContext.resources.getString(R.string.SECRET_KEY)
        )
        binding.memoView.text = memo.memo
        hideEmptyItem()
        binding.pwLayout.setOnClickListener {
            binding.pwText.text = "비밀번호"
            binding.pwView.inputType = InputType.TYPE_CLASS_TEXT
        }

        // 버튼 세팅
        binding.editBtn.setOnClickListener {
            val intent = Intent(applicationContext, MemoEditActivity::class.java)
            intent.putExtra("type", "update")
            intent.putExtra("memo", memo)
            startActivity(intent)
            finish()
        }
        binding.deleteBtn.setOnClickListener {
            viewModel.delete(memo)
            Toast.makeText(applicationContext, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
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
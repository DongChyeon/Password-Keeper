package com.dongchyeon.passwordkeeper.presentation.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.ActivityMemoDetailBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseActivity
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MemoDetailViewModel
import com.dongchyeon.passwordkeeper.util.AES256Chiper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MemoDetailActivity : BaseActivity<ActivityMemoDetailBinding>(R.layout.activity_memo_detail) {
    private val viewModel: MemoDetailViewModel by viewModels()
    private var memoId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        viewModel.loadMemo(intent.getLongExtra("memo", 0L))

        viewModel.isDeleted.observe(this) { isDeleted ->
            if (isDeleted) finish()
        }

        viewModel.toastMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.memo.observe(this) { memo ->
            memoId = memo.id

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
                intent.putExtra("memo", memo.id)
                startActivity(intent)
                finish()
            }
            binding.deleteBtn.setOnClickListener {
                viewModel.delete(memo)
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
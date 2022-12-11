package com.dongchyeon.passwordkeeper.presentation.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.databinding.ActivityMemoEditBinding
import com.dongchyeon.passwordkeeper.presentation.base.BaseActivity
import com.dongchyeon.passwordkeeper.presentation.viewmodel.MemoEditViewModel
import com.dongchyeon.passwordkeeper.util.AES256Chiper
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MemoEditActivity : BaseActivity<ActivityMemoEditBinding>(R.layout.activity_memo_edit) {
    private val viewModel: MemoEditViewModel by viewModels()
    private lateinit var type: String
    private var memoId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    private fun init() {
        type = intent.getStringExtra("type")!!
        if (type == "insert") {
            Objects.requireNonNull(supportActionBar)?.title = "추가"
        } else {
            Objects.requireNonNull(supportActionBar)?.title = "수정"

            viewModel.loadMemo(intent.getLongExtra("memo", 0L))
            viewModel.memo.observe(this) { memo ->
                memoId = memo.id

                binding.titleEdit.setText(memo.title)
                binding.categoryEdit.setText(memo.category)
                binding.idEdit.setText(memo.uid)
                binding.pwEdit.setText(
                    AES256Chiper.AES_Decode(
                        memo.password,
                        applicationContext.resources.getString(R.string.SECRET_KEY)
                    )
                )
                binding.memoEdit.setText(memo.memo)
            }
        }

        viewModel.isUpserted.observe(this) { success ->
            if (success) finish()
        }

        viewModel.toastMessage.observe(this) {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
        }

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener {
            val titleText = binding.titleEdit.text.toString()
            val categoryText = binding.categoryEdit.text.toString()
            val uidText = binding.idEdit.text.toString()
            // 비밀번호 암호화
            val pwText = AES256Chiper.AES_Encode(
                binding.pwEdit.text.toString(),
                applicationContext.resources.getString(R.string.SECRET_KEY)
            )
            val memoText = binding.memoEdit.text.toString()

            if (type == "update") {
                viewModel.update(memoId, titleText, categoryText, uidText, pwText, memoText)
            } else {
                viewModel.insert(titleText, categoryText, uidText, pwText, memoText)
            }
        }
    }
}
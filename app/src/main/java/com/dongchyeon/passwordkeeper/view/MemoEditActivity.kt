package com.dongchyeon.passwordkeeper.view

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dongchyeon.passwordkeeper.R
import com.dongchyeon.passwordkeeper.data.room.Memo
import com.dongchyeon.passwordkeeper.databinding.ActivityMemoEditBinding
import com.dongchyeon.passwordkeeper.util.AES256Chiper
import com.dongchyeon.passwordkeeper.viewmodel.MemoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MemoEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMemoEditBinding
    private lateinit var memo: Memo
    private lateinit var type: String
    private val viewModel: MemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        init()
    }

    private fun init() {
        type = intent.getStringExtra("type")!!

        if (type == "insert") {
            Objects.requireNonNull(supportActionBar)?.title = "추가"
        } else {
            Objects.requireNonNull(supportActionBar)?.title = "편집"
            // MemoViewActivity로부터 인텐트를 넘겨받음
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                memo = intent.getParcelableExtra("memo", Memo::class.java)!!
            } else {
                memo = intent.getParcelableExtra<Memo>("memo")!!
            }

            binding.titleEdit.setText(memo.title)
            binding.categoryEdit.setText(memo.category)
            binding.idEdit.setText(memo.uid)
            binding.pwEdit.setText(AES256Chiper.AES_Decode(memo.password, applicationContext.resources.getString(R.string.SECRET_KEY)))
            binding.memoEdit.setText(memo.memo)
        }

        // 버튼 세팅
        binding.confirmBtn.setOnClickListener {
            val titleText = binding.titleEdit.text.toString()
            val categoryText = binding.categoryEdit.text.toString()
            val uidText = binding.idEdit.text.toString()
            // 비밀번호 암호화
            val pwText = AES256Chiper.AES_Encode(binding.pwEdit.text.toString(), applicationContext.resources.getString(R.string.SECRET_KEY))
            val memoText = binding.memoEdit.text.toString()

            if (categoryText == "전체 보기" || categoryText == "새 항목 추가" || categoryText == "비밀번호 변경") {
                Toast.makeText(applicationContext, "불가능한 카테고리명입니다.", Toast.LENGTH_SHORT).show()
            } else if (titleText == "") {
                Toast.makeText(applicationContext, "제목은 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            } else if (categoryText == "") {
                Toast.makeText(applicationContext, "카테고리는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            } else if (uidText == "") {
                Toast.makeText(applicationContext, "아이디는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            } else if (binding.pwEdit.text.toString() == "") {
                Toast.makeText(applicationContext, "비밀번호는 필수 입력 항목입니다.", Toast.LENGTH_SHORT).show()
            } else if (type == "update") {
                memo.title = titleText
                memo.uid = uidText
                memo.category = categoryText
                memo.password = pwText
                memo.memo = memoText
                viewModel.update(memo) // 이미 있는 아이템일 경우 업데이트

                Toast.makeText(applicationContext, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val item = Memo(titleText, categoryText, uidText, pwText, memoText)
                viewModel.insert(item)
                Toast.makeText(applicationContext, "추가되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}
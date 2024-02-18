package com.example.a_write

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.a_write.api.DiaryBody
import com.example.a_write.api.DiaryService
import com.example.a_write.databinding.FragmentWriteBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class WriteFragment : Fragment() {

    private val REQUEST_CODE = 100
    private lateinit var binding: FragmentWriteBinding
    private var imagePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)

        val selectedThemeIndex = arguments?.getInt("selectedThemeIndex") // 테마값 받아오기

        val WriteActivity = activity as? WriteActivity //날짜값 받아오기
        val date = WriteActivity?.selectedDate
        val year = WriteActivity?.selectedYear
        val month = WriteActivity?.selectedMonth

        // selectedThemeIndex에 따라 배경 이미지 변경 (후에 디자인이 추가된 테마가 만들어진다면 변경하면 됨)
        val backgroundDrawable = when (selectedThemeIndex) {
            0 -> R.drawable.theme_background_blue
            1 -> R.drawable.theme_background_green
            2 -> R.drawable.theme_background_yellow
            3 -> R.drawable.theme_background_red
            else -> R.drawable.theme_background_white
        }

        binding.backgroundviewWritefragment.setImageResource(backgroundDrawable)

        binding.imageButton1.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_CODE)
        }

        binding.saveBtn.setOnClickListener {
            val title = binding.inputDiaryTitle.text.toString()
            val content = binding.inputDiaryContent.text.toString()
            val secret = "false" // 추후 수정
            val theme = selectedThemeIndex.toString()
            val date = "$year-$month-$date"

            val file = File(imagePath ?: "")
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imgUrl = MultipartBody.Part.createFormData("imgUrl", file.name, requestFile)

            val diaryBody = DiaryBody(title, content, imgUrl, secret, theme, date)

            context?.let {
                val diaryService = DiaryService(it)
                diaryService.postDiary(diaryBody)
            }
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImageUri = data.data
            if (selectedImageUri != null) {
                binding.diaryImg.setImageURI(selectedImageUri)
                imagePath = getPathFromUri(requireContext(), selectedImageUri) // 이미지 절대 경로를 imagePath에 저장
            }
        }
    }

    private fun getPathFromUri(context: Context, uri: Uri): String? {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(uri, proj, null, null, null)
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            return cursor?.getString(columnIndex!!)
        } finally {
            cursor?.close()
        }
    }
}


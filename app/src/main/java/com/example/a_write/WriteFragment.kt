package com.example.a_write

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.a_write.api.DiaryService
import com.example.a_write.databinding.FragmentWriteBinding
import java.io.File

class WriteFragment : Fragment() {

    private val PERMISSION_CODE = 100
    private val REQUEST_CODE = 100
    private lateinit var binding: FragmentWriteBinding
    private var imagePath: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWriteBinding.inflate(inflater, container, false)

        val selectedThemeIndex = arguments?.getInt("selectedThemeIndex") // 테마값 받아오기

        val writeActivity = activity as? WriteActivity //날짜값 받아오기
        val date = writeActivity?.selectedDate
        val year = writeActivity?.selectedYear
        val month = writeActivity?.selectedMonth
        val formattedMonth = String.format("%02d", month)

        binding.writeHomeArrowIv.setOnClickListener { //뒤로가기 버튼
            activity?.onBackPressed()
        }

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
            requestPermission()
            openGallery()
        }

        binding.saveBtn.setOnClickListener {
            val diaryData = mapOf(
                "title" to binding.inputDiaryTitle.text.toString(),
                "content" to binding.inputDiaryContent.text.toString(),
                "secret" to "false",
                "theme" to selectedThemeIndex.toString(),
                "date" to "$year-$formattedMonth-$date"
            )

            val filePath = imagePath?.let { getPathFromUri(requireContext(), it) }
            val file = File(filePath ?: "")

            context?.let {
                val diaryService = DiaryService(it)
                diaryService.postDiary(diaryData, file)
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
                imagePath = selectedImageUri
            }
        }
    }

    private fun getPathFromUri(context: Context, uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return ""
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            PERMISSION_CODE
        )
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE)
    }
}



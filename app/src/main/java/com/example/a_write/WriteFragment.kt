package com.example.a_write

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
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


            //LoadingFragment 보여주고 싶을때 추가하시면 됩니다/ 버튼눌렀을때 마지막에 실행되어야할것같아서 여기넣었습니다
            showLoading()

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
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_MEDIA_IMAGES
            ),
            PERMISSION_CODE
        )
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE)
    }


    //Loading화면 띄우기
    private fun showLoading() {
        val fragmentManager = (context as WriteActivity).supportFragmentManager
        val loadingFragment = LoadingFragment.newInstance("", "")
        fragmentManager.beginTransaction()
            .add(R.id.write_activity_lo, loadingFragment, "LOADING_FRAGMENT")
            .commitAllowingStateLoss()
    }

    //Loading화면 닫기. onActivityResult에서 request정리하시는 것 같은데
    //onResponse 나 onFailure, 두 상황 모두에 이 함수가 들어가야합니다.
    private fun hideLoading() {
        val fragmentManager = (context as WriteActivity).supportFragmentManager
        val loadingFragment = fragmentManager.findFragmentByTag("LOADING_FRAGMENT")
        if (loadingFragment != null) {
            fragmentManager.beginTransaction()
                .remove(loadingFragment)
                .commitAllowingStateLoss()
        }
    }
}



package com.example.a_write

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
            val title = binding.inputDiaryTitle.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val content = binding.inputDiaryContent.text.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val secret = "false".toRequestBody("text/plain".toMediaTypeOrNull()) // 추후 수정
            val theme = selectedThemeIndex.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val date = "$year-$month-$date".toRequestBody("text/plain".toMediaTypeOrNull())

            val file = File(binding.diaryImg.tag.toString())
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val imgUrl = MultipartBody.Part.createFormData("imgUrl", file.name, requestFile)

            val diaryBody = DiaryBody(title, content, imgUrl, secret, theme, date)

            context?.let {
                val diaryService = DiaryService(it)
                diaryService.postDiary(diaryBody)
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
            binding.diaryImg.setImageURI(selectedImageUri)
            binding.diaryImg.tag = selectedImageUri?.path // 이미지 경로를 tag에 저장
        }
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

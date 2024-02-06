package com.example.a_write

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentDiaryDetailBinding
import com.squareup.picasso.Picasso

class DiaryDetailFragment : Fragment() {

    companion object {
        private const val ARG_POST = "arg_post"

        fun newInstance(diary: Diary): DiaryDetailFragment {
            val fragment = DiaryDetailFragment()
            val bundle = Bundle().apply {
                putParcelable(ARG_POST, diary)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentDiaryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiaryDetailBinding.inflate(inflater, container, false)

        val diary: Diary? = arguments?.getParcelable(ARG_POST) as? Diary

        diary?.let {
            Log.d("일기 데이터", it.toString())
            binding.diaryDetailTitleTv.text = it.title
            binding.diaryDetailContentTv.text = it.content
            Picasso.get().load(it.img).into(binding.diaryDetailImgIv)
            binding.diaryDetailNicknameTv.text = it.user
            binding.diaryDetailProfileIv.setImageResource(getProfileImageResourceId(it.profile))
            updateHeartVisibility(it.isSaved)
        } ?: Log.d("일기 데이터", "일기를 불러오는 데 실패했습니다.")

        // 이전 버튼 선택
        val previousArrowImageView: ImageView = binding.previousArrowIv
        previousArrowImageView.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        // 이미지 전체보기 다이얼로그
        val diaryDetailImageView: ImageView = binding.diaryDetailImgIv
        diaryDetailImageView.setOnClickListener {
            showImageDialog(diaryDetailImageView.drawable)
        }

        // 일기 보관 여부 관리
        val heartOffImageView: ImageView = binding.diaryDetailHeartOffIv
        val heartOnImageView: ImageView = binding.diaryDetailHeartOnIv

        heartOffImageView.setOnClickListener {
            diary?.let {
                it.isSaved = true
                updateHeartVisibility(true)
            }
        }

        heartOnImageView.setOnClickListener {
            diary?.let {
                it.isSaved = false
                updateHeartVisibility(false)
            }
        }

        return binding.root
    }

    private fun updateHeartVisibility(isSaved: Boolean) {
        binding.diaryDetailHeartOnIv.visibility = if (isSaved) View.VISIBLE else View.GONE
        binding.diaryDetailHeartOffIv.visibility = if (isSaved) View.GONE else View.VISIBLE
    }

    private fun showImageDialog(drawable: Drawable) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_diary_detail_img)

        val imageView = dialog.findViewById<ImageView>(R.id.diary_detail_dialog_iv)
        imageView.setImageDrawable(drawable)
        imageView.setOnClickListener { dialog.dismiss() }
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

}
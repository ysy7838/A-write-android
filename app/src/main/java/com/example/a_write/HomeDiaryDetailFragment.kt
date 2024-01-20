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
import com.example.a_write.databinding.FragmentHomeDiaryDetailBinding

class HomeDiaryDetailFragment : Fragment() {

    companion object {
        private const val ARG_POST = "arg_post"

        fun newInstance(post: Post): HomeDiaryDetailFragment {
            val fragment = HomeDiaryDetailFragment()
            val bundle = Bundle().apply {
                putParcelable(ARG_POST, post)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var binding: FragmentHomeDiaryDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeDiaryDetailBinding.inflate(inflater, container, false)

        val post: Post? = arguments?.getParcelable(ARG_POST) as? Post

        post?.let {
            Log.d("일기 데이터", it.toString())
            binding.diaryDetailTitleTv.text = it.title
            binding.diaryDetailContentTv.text = it.content
            binding.diaryDetailNicknameTv.text = it.user
        } ?: Log.d("일기 데이터", "Post is null")

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
            heartOffImageView.visibility = View.GONE
            heartOnImageView.visibility = View.VISIBLE
        }

        heartOnImageView.setOnClickListener {
            heartOnImageView.visibility = View.GONE
            heartOffImageView.visibility = View.VISIBLE
        }


        return binding.root
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
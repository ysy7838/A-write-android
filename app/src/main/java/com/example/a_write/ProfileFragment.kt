package com.example.a_write

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_write.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var postDatas = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val settingImageView: ImageView = binding.icSetting
        settingImageView.setOnClickListener {
            val intent = Intent(requireContext(), ProfileSettingActivity::class.java)
            startActivity(intent)
        }

        //데이터 리스트 생성 더미 데이터
        postDatas.apply {
            add(Post("MELTING POINT", "ZEROBASEONE(제로베이스원)"))
            add(Post("MELTING", "ZEROBASEONE(제로베이스원)"))
            add(Post("POINT", "ZEROBASEONE(제로베이스원)"))
        }

        val topPostRVAdapter = TopPostRVAdapter(postDatas)
        binding.profileTopPostsRv.adapter = topPostRVAdapter
        binding.profileTopPostsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val allPostRVAdapter = AllPostRVAdapter(postDatas)
        binding.profileAllPostsRv.adapter = allPostRVAdapter
        binding.profileAllPostsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }
}
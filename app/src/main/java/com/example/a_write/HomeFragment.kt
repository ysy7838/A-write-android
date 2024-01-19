package com.example.a_write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_write.databinding.FragmentHomeBinding
import java.util.ArrayList

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var postDatas = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        postDatas.apply {
            add(Post("MELTING POINT", "", "ZEROBASEONE(제로베이스원)", false))
            add(Post("MELTING", "", "ZEROBASEONE(제로베이스원)", false))
            add(Post("POINT", "", "ZEROBASEONE(제로베이스원)", true))
        }

        // 전체 일기글 RV
        val homeDiaryPostRVAdapter = HomeDiaryPostRVAdapter(postDatas)
        binding.homeDiaryPostsRv.adapter = homeDiaryPostRVAdapter
        binding.homeDiaryPostsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

}
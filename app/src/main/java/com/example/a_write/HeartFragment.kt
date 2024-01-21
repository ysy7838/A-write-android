package com.example.a_write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_write.databinding.FragmentHeartBinding
import java.util.ArrayList

class HeartFragment : Fragment() {

    private lateinit var binding: FragmentHeartBinding
    private var postDatas = ArrayList<Post>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeartBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        postDatas.apply {
            add(Post("제목", "내용", "애플","2024.1.18",true))
            add(Post("MELTING", "19일 일기 내용", "ZEROBASEONE(제로베이스원)", "2024.1.19",true))
            add(Post("POINT", "20일 일기 내용", "ZEROBASEONE(제로베이스원)", "2024.1.20",true))
        }

        // 보관함 RV
        val heartDiaryPostRVAdapter = HeartDiaryPostRVAdapter(postDatas)
        binding.heartDiaryPostsRv.adapter = heartDiaryPostRVAdapter
        binding.heartDiaryPostsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

}
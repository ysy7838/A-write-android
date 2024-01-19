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
            add(Post("MELTING POINT", "", "ZEROBASEONE(제로베이스원)", true))
            add(Post("MELTING", "", "ZEROBASEONE(제로베이스원)", true))
            add(Post("POINT", "", "ZEROBASEONE(제로베이스원)", true))
        }

        // 보관함 RV
        val heartDiaryPostRVAdapter = HeartDiaryPostRVAdapter(postDatas)
        binding.heartDiaryPostsRv.adapter = heartDiaryPostRVAdapter
        binding.heartDiaryPostsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

}
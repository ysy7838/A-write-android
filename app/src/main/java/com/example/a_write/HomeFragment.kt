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
    private var diaryData = ArrayList<Diary>()
    private var isDataAdded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        if (!isDataAdded) {
            diaryData.apply {
                add(Diary("여행 2일차", "1월 20일. 확실히, 여행은 단순한 관광 이상이다. 여행은 삶에 관한 상념들에 계속해서 일어나는 깊고, 영구적인 변화이다.","https://images.unsplash.com/photo-1647891938250-954addeb9c51?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 3, "애플",2, "2024-01-20", true))
                add(Diary("19일 맑음", "파란 하늘","https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=11288960&filePath=L2Rpc2sxL25ld2RhdGEvMjAxNS8wMi9DTFM2OS9OVVJJXzAwMV8wNDQ2X251cmltZWRpYV8yMDE1MTIwMw==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10006", 1, "바나나", 3,"2024-01-19", false))
                add(Diary("깜냥이", "웅냥냥 고양이","https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202306/25/488f9638-800c-4bac-ad65-82877fbff79b.jpg", 4, "워터멜론", 4,"2024-01-22", false))
            }
            isDataAdded = true
        }

        // 전체 일기글 RV
        val homePreviewDiaryRVAdapter = HomePreviewDiaryRVAdapter(diaryData) { diary: Diary ->
            navigateToAnotherPage(diary)
        }
        binding.homeDiaryPostsRv.adapter = homePreviewDiaryRVAdapter
        binding.homeDiaryPostsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    private fun navigateToAnotherPage(diary: Diary) {
        val fragment = DiaryDetailFragment.newInstance(diary)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
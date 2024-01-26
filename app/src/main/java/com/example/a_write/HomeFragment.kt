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
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 데이터 리스트 생성 더미 데이터
        if (!isDataAdded) {
            diaryData.apply {
                add(Diary("제목", "내용", "애플","2024.1.18",false))
                add(Diary("MELTING", "19일 일기 내용", "ZEROBASEONE(제로베이스원)", "2024.1.19",false))
                add(Diary("POINT", "20일 일기 내용", "ZEROBASEONE(제로베이스원)", "2024.1.20",true))
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
        val fragment = HomeDiaryDetailFragment.newInstance(diary)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
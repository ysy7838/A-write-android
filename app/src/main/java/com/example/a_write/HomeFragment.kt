package com.example.a_write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.api.HomeDataListener
//import com.example.a_write.api.DiaryResult
import com.example.a_write.api.DiaryService
import com.example.a_write.database.DiaryResult
import com.example.a_write.database.getHomeDiary
import com.example.a_write.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    //    private val diaryService = DiaryService()
    private lateinit var recyclerView: RecyclerView
    private var homeRVA: HomePreviewDiaryRVAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        diaryService.getHomeList(this)

        // 전체 일기글 RV (db)
        recyclerView = binding.homeDiaryPostsRv
        homeRVA = HomePreviewDiaryRVAdapter { diary ->
            navigateToAnotherPage(diary)
        }

        getHomeDiary(homeRVA!!)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = homeRVA
        }

        return binding.root
    }

//    override fun onDataLoaded(diaries: List<DiaryResult>) {
//        // 전체 일기글 RV (api)
//        val homePreviewDiaryRVAdapter = HomePreviewDiaryRVAdapter(diaries) { diary: DiaryResult ->
//            navigateToAnotherPage(diary)
//        }
//        binding.homeDiaryPostsRv.adapter = homePreviewDiaryRVAdapter
//        binding.homeDiaryPostsRv.layoutManager =
//            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//    }

    private fun navigateToAnotherPage(diary: DiaryResult) {
        val fragment = DiaryDetailFragment.newInstance(diary)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
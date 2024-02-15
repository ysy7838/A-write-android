package com.example.a_write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_write.api.HomeDataListener
import com.example.a_write.api.DiaryResult
import com.example.a_write.api.DiaryService
import com.example.a_write.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), HomeDataListener {

    private lateinit var binding: FragmentHomeBinding
    private val diaryService = DiaryService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        diaryService.getHomeList(this)

        return binding.root
    }

    override fun onDataLoaded(diaries: List<DiaryResult>) {
        // 전체 일기글 RV
        val homePreviewDiaryRVAdapter = HomePreviewDiaryRVAdapter(diaries) { diary: DiaryResult ->
            navigateToAnotherPage(diary)
        }
        binding.homeDiaryPostsRv.adapter = homePreviewDiaryRVAdapter
        binding.homeDiaryPostsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    private fun navigateToAnotherPage(diary: DiaryResult) {
        val fragment = DiaryDetailFragment.newInstance(diary)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
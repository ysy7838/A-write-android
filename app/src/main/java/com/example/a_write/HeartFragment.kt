package com.example.a_write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_write.api.DiaryResult
import com.example.a_write.api.DiaryService
import com.example.a_write.api.HeartDataListener
import com.example.a_write.databinding.FragmentHeartBinding

class HeartFragment(private val diaryService: DiaryService) : Fragment(), HeartDataListener {

    private lateinit var binding: FragmentHeartBinding
    private lateinit var heartPreviewDiaryRVAdapter: HeartPreviewDiaryRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeartBinding.inflate(inflater, container, false)

        //  보관함 RV
        heartPreviewDiaryRVAdapter = HeartPreviewDiaryRVAdapter(
            emptyList(),
            diaryService,
            { diary -> navigateToAnotherPage(diary) },
            { diaryService.getHeartList(this) }
        )

        binding.heartDiaryPostsRv.adapter = heartPreviewDiaryRVAdapter
        binding.heartDiaryPostsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        diaryService.getHeartList(this)

        return binding.root
    }

    override fun onDataLoaded(diaries: List<DiaryResult>) {
        heartPreviewDiaryRVAdapter.updateData(diaries)
    }

    private fun navigateToAnotherPage(diary: DiaryResult) {
        val fragment = DiaryDetailFragment.newInstance(diary)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}
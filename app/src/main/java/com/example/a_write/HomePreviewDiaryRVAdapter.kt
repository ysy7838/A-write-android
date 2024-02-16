package com.example.a_write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.api.DiaryResult
import com.example.a_write.api.DiaryService
import com.example.a_write.databinding.ItemPreviewDiaryBinding

class HomePreviewDiaryRVAdapter(
    private val diaries: List<DiaryResult>,
    private val onItemClicked: (DiaryResult) -> Unit
) :
    RecyclerView.Adapter<HomePreviewDiaryRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): HomePreviewDiaryRVAdapter.ViewHolder {
        val binding: ItemPreviewDiaryBinding =
            ItemPreviewDiaryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomePreviewDiaryRVAdapter.ViewHolder, position: Int) {
        holder.bind(diaries[position], onItemClicked)
    }

    override fun getItemCount(): Int {
        return diaries.size
    }

    class ViewHolder(private val binding: ItemPreviewDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: DiaryResult, onItemClicked: (DiaryResult) -> Unit) {
            binding.itemDiaryPostTitleTv.text = diary.title
            binding.itemDiaryPostContentTv.text = diary.content
            binding.itemDiaryProfileIv.setImageResource(getProfileImageResourceId(diary.authorProfile))
            updateHeartVisibility(diary.heartby)

            binding.root.setOnClickListener {
                onItemClicked(diary)
            }

            val diaryService = DiaryService()

            // 일기 보관 여부 관리
            binding.itemDiaryHeartOnIv.setOnClickListener {
                diary.heartby = false
                updateHeartVisibility(false)
                diaryService.deleteDiaryHeart(diary.diaryId)
            }

            binding.itemDiaryHeartOffIv.setOnClickListener {
                diary.heartby = true
                updateHeartVisibility(true)
                diaryService.postDiaryHeart(diary.diaryId)
            }
        }

        private fun updateHeartVisibility(isSaved: Boolean) {
            binding.itemDiaryHeartOnIv.visibility = if (isSaved) View.VISIBLE else View.GONE
            binding.itemDiaryHeartOffIv.visibility = if (isSaved) View.GONE else View.VISIBLE
        }
    }

}
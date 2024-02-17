package com.example.a_write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.api.DiaryResult
import com.example.a_write.api.DiaryService
import com.example.a_write.databinding.ItemPreviewDiaryBinding
import kotlinx.coroutines.*

class HeartPreviewDiaryRVAdapter(
    private var diaries: List<DiaryResult>,
    private val diaryService: DiaryService,
    private val onItemClicked: (DiaryResult) -> Unit,
    private val onLikeClicked: () -> Unit
) :
    RecyclerView.Adapter<HeartPreviewDiaryRVAdapter.ViewHolder>() {
    fun updateData(newDiaries: List<DiaryResult>) {
        diaries = newDiaries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): HeartPreviewDiaryRVAdapter.ViewHolder {
        val binding: ItemPreviewDiaryBinding =
            ItemPreviewDiaryBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )

        return ViewHolder(binding, diaryService)
    }

    override fun onBindViewHolder(holder: HeartPreviewDiaryRVAdapter.ViewHolder, position: Int) {
        holder.bind(diaries[position], this, onItemClicked)
    }

    inner class ViewHolder(
        private val binding: ItemPreviewDiaryBinding,
        private val diaryService: DiaryService
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            diary: DiaryResult,
            adapter: HeartPreviewDiaryRVAdapter,
            onItemClicked: (DiaryResult) -> Unit
        ) {
            binding.itemDiaryPostTitleTv.text = diary.title
            binding.itemDiaryPostContentTv.text = diary.content
            binding.itemDiaryProfileIv.setImageResource(getProfileImageResourceId(diary.authorProfile))
            binding.itemDiaryHeartOnIv.visibility = if (diary.heartby) View.VISIBLE else View.GONE
            binding.itemDiaryHeartOffIv.visibility = if (diary.heartby) View.GONE else View.VISIBLE

            binding.itemDiaryHeartOnIv.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {
                        diaryService.deleteDiaryHeart(diary.diaryId)
                    }
                    onLikeClicked()
                }
            }

            binding.root.setOnClickListener {
                onItemClicked(diary)
            }
        }
    }

    override fun getItemCount(): Int {
        return diaries.size
    }

}
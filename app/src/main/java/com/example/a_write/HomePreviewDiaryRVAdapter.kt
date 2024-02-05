package com.example.a_write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemPreviewDiaryBinding

class HomePreviewDiaryRVAdapter(
    private val diaries: List<Diary>,
    private val onItemClicked: (Diary) -> Unit
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

    class ViewHolder(private val binding: ItemPreviewDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary, onItemClicked: (Diary) -> Unit) {
            binding.itemDiaryPostTitleTv.text = diary.title
            binding.itemDiaryPostContentTv.text = diary.content
            binding.itemDiaryProfileIv.setImageResource(getProfileImageResourceId(diary.profile))
            updateHeartVisibility(diary.isSaved)

            binding.root.setOnClickListener {
                onItemClicked(diary)
            }

            // 일기 보관 여부 관리 (서버 연결하면 코드 수정하기)
            binding.itemDiaryHeartOnIv.setOnClickListener {
                diary.isSaved = false
                updateHeartVisibility(false)
            }

            binding.itemDiaryHeartOffIv.setOnClickListener {
                diary.isSaved = true
                updateHeartVisibility(true)
            }
        }

        private fun updateHeartVisibility(isSaved: Boolean) {
            binding.itemDiaryHeartOnIv.visibility = if (isSaved) View.VISIBLE else View.GONE
            binding.itemDiaryHeartOffIv.visibility = if (isSaved) View.GONE else View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return diaries.size
    }

}
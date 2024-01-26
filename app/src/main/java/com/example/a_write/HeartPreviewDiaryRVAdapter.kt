package com.example.a_write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemPreviewDiaryBinding

class HeartPreviewDiaryRVAdapter(
    private val diaries: MutableList<Diary>,
    private val onItemClicked: (Diary) -> Unit
) :
    RecyclerView.Adapter<HeartPreviewDiaryRVAdapter.ViewHolder>() {
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

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeartPreviewDiaryRVAdapter.ViewHolder, position: Int) {
        holder.bind(diaries[position], this, onItemClicked)
    }

    class ViewHolder(private val binding: ItemPreviewDiaryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary, adapter: HeartPreviewDiaryRVAdapter, onItemClicked: (Diary) -> Unit) {
            binding.itemDiaryPostTitleTv.text = diary.title
            binding.itemDiaryPostContentTv.text = diary.content
            binding.itemDiaryHeartOnIv.visibility = if (diary.isSaved) View.VISIBLE else View.GONE
            binding.itemDiaryHeartOffIv.visibility = if (diary.isSaved) View.GONE else View.VISIBLE

            binding.itemDiaryHeartOnIv.setOnClickListener {
                val position = adapter.removePost(diary)
                adapter.notifyItemRemoved(position)
            }

            binding.root.setOnClickListener {
                onItemClicked(diary)
            }
        }
    }

    override fun getItemCount(): Int {
        return diaries.size
    }

    //보관함에 있는 일기 삭제 (서버 연결하면 코드 수정하기)
    fun removePost(diary: Diary): Int {
        val position = diaries.indexOf(diary)
        if (position != -1) {
            diaries.removeAt(position)
        }
        return position
    }

}
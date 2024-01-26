package com.example.a_write

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemTopDiaryBinding

class ProfileTopDiaryRVAdapter(private val diaries: List<Diary>, private val context: Context) : RecyclerView.Adapter<ProfileTopDiaryRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTopDiaryBinding = ItemTopDiaryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(diaries[position])

        holder.itemView.setOnClickListener {
            val diary = diaries[position]
            navigateToAnotherPage(diary)
        }
    }

    private fun navigateToAnotherPage(diary: Diary) {
        val intent = Intent(context, ProfileDiaryActivity::class.java)
        intent.putExtra("diary_title", diary.title)
        intent.putExtra("diary_theme", diary.theme)
        context.startActivity(intent)
    }

    class ViewHolder(private val binding: ItemTopDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary){
            binding.itemPostTitleTv.text = diary.title
            binding.itemPostTimeTv.text = diary.time
        }
    }

    override fun getItemCount(): Int {
        return diaries.size
    }

}
package com.example.a_write

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemTopDiaryBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

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
        intent.putExtra("diary_content", diary.content)
        intent.putExtra("diary_img", diary.img)
        intent.putExtra("diary_theme", diary.theme)
        context.startActivity(intent)
    }

    class ViewHolder(private val binding: ItemTopDiaryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(diary: Diary){
            binding.itemPostTitleTv.text = diary.title
            binding.itemPostTimeTv.text = formatDate(diary.date)
            Picasso.get().load(diary.img).into(binding.itemPostCoverImgIv)
        }

        private fun formatDate(inputDate: String): String {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy.M.d", Locale.getDefault())

            val date = inputFormat.parse(inputDate)
            return outputFormat.format(date!!)
        }
    }

    override fun getItemCount(): Int {
        return diaries.size
    }

}
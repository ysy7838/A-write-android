package com.example.a_write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemDiaryPostBinding

class HomeDiaryPostRVAdapter(
    private val posts: List<Post>,
    private val onItemClicked: (Post) -> Unit
) :
    RecyclerView.Adapter<HomeDiaryPostRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): HomeDiaryPostRVAdapter.ViewHolder {
        val binding: ItemDiaryPostBinding =
            ItemDiaryPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeDiaryPostRVAdapter.ViewHolder, position: Int) {
        holder.bind(posts[position], onItemClicked)
    }

    class ViewHolder(val binding: ItemDiaryPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, onItemClicked: (Post) -> Unit) {
            binding.itemDiaryPostTitleTv.text = post.title
            binding.itemDiaryPostContentTv.text = post.content
            updateHeartVisibility(post)

            binding.root.setOnClickListener {
                onItemClicked(post)
            }

            // 일기 보관 여부 관리 (서버 연결하면 코드 수정하기)
            binding.itemDiaryHeartOnIv.setOnClickListener {
                post.isSaved = false
                updateHeartVisibility(post)
            }

            binding.itemDiaryHeartOffIv.setOnClickListener {
                post.isSaved = true
                updateHeartVisibility(post)
            }
        }

        private fun updateHeartVisibility(post: Post) {
            binding.itemDiaryHeartOnIv.visibility = if (post.isSaved) View.VISIBLE else View.GONE
            binding.itemDiaryHeartOffIv.visibility = if (post.isSaved) View.GONE else View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}
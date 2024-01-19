package com.example.a_write

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemDiaryPostBinding

class HeartDiaryPostRVAdapter(private val posts: MutableList<Post>) :
    RecyclerView.Adapter<HeartDiaryPostRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): HeartDiaryPostRVAdapter.ViewHolder {
        val binding: ItemDiaryPostBinding =
            ItemDiaryPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeartDiaryPostRVAdapter.ViewHolder, position: Int) {
        holder.bind(posts[position], this)
    }

    class ViewHolder(val binding: ItemDiaryPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, adapter: HeartDiaryPostRVAdapter) {
            binding.itemDiaryPostTitleTv.text = post.title
            binding.itemDiaryPostContentTv.text = post.content
            binding.itemDiaryHeartOnIv.visibility = if (post.isSaved) View.VISIBLE else View.GONE
            binding.itemDiaryHeartOffIv.visibility = if (post.isSaved) View.GONE else View.VISIBLE

            binding.itemDiaryHeartOnIv.setOnClickListener {
                val position = adapter.removePost(post)
                adapter.notifyItemRemoved(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    //보관함에 있는 일기 삭제 (서버 연결하면 코드 수정하기)
    fun removePost(post: Post): Int {
        val position = posts.indexOf(post)
        if (position != -1) {
            posts.removeAt(position)
        }
        return position
    }

}
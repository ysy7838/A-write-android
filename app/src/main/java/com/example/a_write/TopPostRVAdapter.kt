package com.example.a_write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemTopPostBinding

class TopPostRVAdapter(private val posts: List<Post>) : RecyclerView.Adapter<TopPostRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TopPostRVAdapter.ViewHolder {
        val binding: ItemTopPostBinding = ItemTopPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopPostRVAdapter.ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    class ViewHolder(val binding: ItemTopPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){
            binding.itemPostTitleTv.text = post.title
            binding.itemPostTimeTv.text = post.time
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}
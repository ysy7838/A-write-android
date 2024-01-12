package com.example.a_write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.databinding.ItemAllPostBinding
import com.example.a_write.databinding.ItemTopPostBinding

class AllPostRVAdapter(private val posts: List<Post>) : RecyclerView.Adapter<AllPostRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AllPostRVAdapter.ViewHolder {
        val binding: ItemAllPostBinding = ItemAllPostBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllPostRVAdapter.ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    class ViewHolder(val binding: ItemAllPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post){
            binding.itemAllPostTitleTv.text = post.title
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}
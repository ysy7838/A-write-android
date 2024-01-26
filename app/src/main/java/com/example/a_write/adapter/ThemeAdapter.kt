package com.example.a_write.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.R

class ThemeAdapter(private val list: ArrayList<String>, private val context: Context) : RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.view_theme, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTheme.text = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgTheme: ImageView = itemView.findViewById(R.id.imgTheme)
        val nameTheme: TextView = itemView.findViewById(R.id.nameTheme)
        //val btChoice: Button = itemView.findViewById(R.id.btChoice)
    }
}
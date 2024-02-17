package com.example.a_write.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.R

class ThemeAdapter(private val list: ArrayList<String>, private val context: Context, private val itemClickListener: (Int) -> Unit) : RecyclerView.Adapter<ThemeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.view_theme, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theme = list[position]
        holder.nameTheme.text = theme

        when (theme) {
            "theme_blue" -> holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_blue))
            "theme_green" -> holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_green))
            "theme_yellow" -> holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_yellow))
            "theme_red" -> holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.theme_red))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.card_view)
        val nameTheme: TextView = itemView.findViewById(R.id.nameTheme)
        //val btChoice: Button = itemView.findViewById(R.id.btChoice)
    }
}
package com.example.a_write.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.R
import java.util.Calendar

class MonthRVAdapter(private val months: List<String>) :
    RecyclerView.Adapter<MonthRVAdapter.MonthViewHolder>() {

    private var calendar = Calendar.getInstance()
    private var curMonth = calendar.get(Calendar.MONTH)


    inner class MonthViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val monthText: TextView = itemView.findViewById(R.id.monthText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_month, parent, false)

        return MonthViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MonthViewHolder, position: Int) {

        holder.monthText.text = months[curMonth]
    }

    override fun getItemCount(): Int = 1

    fun moveToNextMonth(){
        calendar.add(Calendar.MONTH, +1)
        curMonth = calendar.get(Calendar.MONTH)
        notifyDataSetChanged()
    }

    fun moveToPreviousMonth(){
        calendar.add(Calendar.MONTH, -1)
        curMonth = calendar.get(Calendar.MONTH)
        notifyDataSetChanged()
    }
}

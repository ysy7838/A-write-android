package com.example.a_write.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.R

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private var dayList : List<String> = ArrayList()

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val calendarDay = dayList[position]
        holder.itemView.findViewById<TextView>(R.id.item_dayText).text = calendarDay
        // TODO: 클릭 이벤트 설정
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    fun updateItem(item : List<String>) {
        dayList = item
        notifyDataSetChanged()
    }
}

package com.example.a_write.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.a_write.R
import java.util.Calendar

class CalendarRVAdapter(
    private val context: Context,
    private val calendarGrid: GridView,
    private val calendar: Calendar,
    private val onDateClickListener: OnDateClickListener
) : BaseAdapter() {

    private var selectedPosition: Int = -1

    private var year = calendar.get(Calendar.YEAR)
    private var month = calendar.get(Calendar.MONTH)
    private var daysList = getDaysOfMonth(year, month)
    private var currentCalendar = Calendar.getInstance()
    private var curYear = currentCalendar.get(Calendar.YEAR)
    private var curMonth = currentCalendar.get(Calendar.MONTH)

    override fun getCount(): Int {
        return daysList.size
    }

    override fun getItem(position: Int): Any {
        return daysList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val day = getItem(position) as String
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View

        if (convertView == null) {
            // 새로운 셀을 생성
            view = inflater.inflate(R.layout.grid_item_day, null)
        } else {
            // 재사용 가능한 셀이 있으면 그것을 사용
            view = convertView
        }

        val dayTextView: TextView = view.findViewById(R.id.dayTextView)

        // 날짜가 비어 있으면 빈 문자열을 표시
        dayTextView.text = if (day.isNotEmpty()) day else ""

        // 현재 날짜 표시


        val currentDate = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        if (curYear==year&&curMonth==month&& day == currentDate.toString()) {
            dayTextView.setBackgroundResource(R.drawable.today_circle_background)
            dayTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            dayTextView.setTextColor(ContextCompat.getColor(context, R.color.black))
            dayTextView.setBackgroundResource(android.R.color.transparent)
        }

        // 날짜 선택 표시
        if (position == selectedPosition) {
            dayTextView.setBackgroundResource(R.drawable.clicked_circle_background)
        }

        view.setOnClickListener {
            if(day.isNotEmpty()){
                onDateClickListener.onDateClick(day.toInt(), month+1, year)
                selectedPosition = position
            }
            notifyDataSetChanged()
        }

        return view
    }

    // 이전 달로 이동
    fun moveToPreviousMonth() {
        calendar.add(Calendar.MONTH, -1)
        updateCalenderData()
        notifyDataSetChanged()
        applySlideAnimation(true) // 다음 달로 이동하는 애니메이션
    }


    // 다음 달로 이동
    fun moveToNextMonth() {
        calendar.add(Calendar.MONTH, 1)
        updateCalenderData()
        notifyDataSetChanged()
        applySlideAnimation(false) // 이전 달로 이동하는 애니메이션
    }

    private fun updateCalenderData(){
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        daysList = getDaysOfMonth(year, month)
        selectedPosition = -1
    }

    interface OnDateClickListener {
        fun onDateClick(date: Int, month: Int, year: Int)
    }

    private fun applySlideAnimation(toNextMonth: Boolean) {
        val direction = if (toNextMonth) 1 else -1
        val parentWidth = calendarGrid.width // GridLayout의 부모 너비를 기준으로 애니메이션을 적용

        calendarGrid.animate()
            .translationX(parentWidth.toFloat() * direction) // 시작 위치 설정
            .setDuration(0)
            .withEndAction {
                updateCalenderData() // 실제 날짜 변경 로직
                calendarGrid.translationX = -parentWidth.toFloat() * direction // 반대 방향으로 초기화

                // 원래 위치로 슬라이드되며 날짜 보여주기
                calendarGrid.animate()
                    .translationX(0f)
                    .setDuration(300) // 애니메이션 지속 시간 설정
                    .start()
            }
            .start()
    }
}

private fun getDaysOfMonth(year: Int, month: Int): List<String> {
    val daysList = mutableListOf<String>()
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)

    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    for (i in 1..firstDayOfWeek - 1) {
        daysList.add("")
    }

    for (dayOfMonth in 1..lastDayOfMonth) {
        daysList.add(dayOfMonth.toString())
    }

    return daysList
}

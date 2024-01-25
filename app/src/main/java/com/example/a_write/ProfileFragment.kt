package com.example.a_write

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a_write.databinding.FragmentProfileBinding
import java.util.*
import android.widget.BaseAdapter
import androidx.core.content.ContextCompat


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private var diaryData = ArrayList<Diary>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // 환경설정 아이콘 클릭
        val settingImageView: ImageView = binding.icSetting
        settingImageView.setOnClickListener {
            val intent = Intent(requireContext(), ProfileSettingActivity::class.java)
            startActivity(intent)
        }

        // 데이터 리스트 생성 더미 데이터
        diaryData.apply {
            add(Diary("제목", "내용", "애플","2024.1.18",false))
            add(Diary("MELTING", "19일 일기 내용", "ZEROBASEONE(제로베이스원)", "2024.1.19",false))
            add(Diary("POINT", "20일 일기 내용", "ZEROBASEONE(제로베이스원)", "2024.1.20",true))
        }

        // 인기 일기글 RV
        val profileTopPostRVAdapter = ProfileTopDiaryRVAdapter(diaryData, requireContext())
        binding.profileTopPostsRv.adapter = profileTopPostRVAdapter
        binding.profileTopPostsRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // 캘린더 설정
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val calendarGridView: GridView = binding.calendarGv

        val daysList = getDaysOfMonth(year, month)
        val adapter = CalendarAdapter(
            requireContext(),
            daysList,
            object : CalendarAdapter.OnDateClickListener {
                override fun onDateClick(date: String) {
                    val intent = Intent(requireContext(), ProfileDiaryActivity::class.java)
                    intent.putExtra("selectedDate", date)
                    intent.putExtra("selectedYear", year)
                    intent.putExtra("selectedMonth", month + 1)
                    startActivity(intent)
                }
            })

        calendarGridView.adapter = adapter

        return binding.root
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

}

class CalendarAdapter(
    private val context: Context,
    private val daysList: List<String>,
    private val onDateClickListener: OnDateClickListener
) : BaseAdapter() {

    private var selectedPosition: Int = -1

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
        if (day == currentDate.toString()) {
            dayTextView.setBackgroundResource(R.drawable.today_circle_background)
            dayTextView.setTextColor(ContextCompat.getColor(context, R.color.white))
        } else {
            dayTextView.setBackgroundResource(android.R.color.transparent)
        }

        // 날짜 선택 표시
        if (position == selectedPosition) {
            dayTextView.setBackgroundResource(R.drawable.clicked_circle_background)
        }

        view.setOnClickListener {
            onDateClickListener.onDateClick(day)
            selectedPosition = position
            notifyDataSetChanged()
        }

        return view
    }

    interface OnDateClickListener {
        fun onDateClick(date: String)
    }
}

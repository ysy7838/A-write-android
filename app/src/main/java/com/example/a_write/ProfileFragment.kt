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
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        // 환경설정 아이콘 클릭
        val settingImageView: ImageView = binding.icSetting
        settingImageView.setOnClickListener {
            val intent = Intent(requireContext(), ProfileSettingActivity::class.java)
            startActivity(intent)
        }

        // 데이터 리스트 생성 더미 데이터
        diaryData.apply {
            add(Diary("여행 2일차", "1월 20일. 확실히, 여행은 단순한 관광 이상이다. 여행은 삶에 관한 상념들에 계속해서 일어나는 깊고, 영구적인 변화이다.","https://images.unsplash.com/photo-1647891938250-954addeb9c51?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 3, "애플",2, "2024-01-20", true))
            add(Diary("19일 맑음", "파란 하늘","https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=11288960&filePath=L2Rpc2sxL25ld2RhdGEvMjAxNS8wMi9DTFM2OS9OVVJJXzAwMV8wNDQ2X251cmltZWRpYV8yMDE1MTIwMw==&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10006", 1, "바나나", 3,"2024-01-19", false))
            add(Diary("깜냥이", "웅냥냥 고양이","https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202306/25/488f9638-800c-4bac-ad65-82877fbff79b.jpg", 4, "워터멜론", 4,"2024-01-22", false))
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

                    // 임시 데이터
                    intent.putExtra("diary_title", "여행 2일차")
                    intent.putExtra("diary_content","1월 20일. 확실히, 여행은 단순한 관광 이상이다. 여행은 삶에 관한 상념들에 계속해서 일어나는 깊고, 영구적인 변화이다.")
                    intent.putExtra("diary_img", "https://images.unsplash.com/photo-1647891938250-954addeb9c51?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D")
                    intent.putExtra("diary_theme", 3)

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

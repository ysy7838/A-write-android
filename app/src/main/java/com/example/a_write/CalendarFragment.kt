package com.example.a_write


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.a_write.adapter.CalendarRVAdapter
import com.example.a_write.adapter.MonthRVAdapter
import com.example.a_write.databinding.FragmentWriteBinding
import java.util.Calendar




class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentWriteBinding.inflate(inflater, container, false)

        // 캘린더 설정
        val calendar = Calendar.getInstance()

        //month자료
        val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        val viewPager: ViewPager2 = binding.writeMonthVp
        val monthAdapter = MonthRVAdapter(months)
        viewPager.isUserInputEnabled = false


        //gridview
        val calendarGridView: GridView = binding.writeCalendarGv

        val adapter = CalendarRVAdapter(
            requireContext(),
            calendarGridView,
            calendar,
            object : CalendarRVAdapter.OnDateClickListener {
                override fun onDateClick(date: Int, month: Int, year: Int) {
                    binding.writeNextBt.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    binding.writeNextBt.setOnClickListener {
                        (activity as? WriteActivity)?.setSelectedData(date, year, month)

                        (context as WriteActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.write_activity_lo, ThemeFragment())
                            .addToBackStack(null) // themeFragment로 간 후, 뒤로가기를 누를 시 다시 날짜 선택하게끔/ 없애도 됨
                            .commit()
                    }
                }
            })

        calendarGridView.adapter = adapter
        viewPager.adapter = monthAdapter


        binding.writeCalendarNextIv.setOnClickListener {
            monthAdapter.moveToNextMonth()
            adapter.moveToNextMonth()
        }

        binding.writeCalendarPreviousIv.setOnClickListener {
            monthAdapter.moveToPreviousMonth()
            adapter.moveToPreviousMonth()
        }

        binding.writeHomeArrowIv.setOnClickListener {
            activity?.finish()
        }


        return binding.root
    }



}

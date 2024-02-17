package com.example.a_write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class WriteActivity : AppCompatActivity() {

    private var date: Int? = null
    private var year: Int? = null
    private var month: Int? = null

    // 다른 fragment로 값을 전달하기 위해 getter 메소드 추가
    val selectedDate: Int? get() = date
    val selectedYear: Int? get() = year
    val selectedMonth: Int? get() = month

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        supportFragmentManager.beginTransaction()
            .replace(R.id.write_activity_lo, CalendarFragment())
            .commitAllowingStateLoss()
    }


    fun setSelectedData(date: Int, year: Int, month: Int) {
        this.date = date
        this.year = year
        this.month = month
    }


    /* 다른 fragment에서 date, month, year접근하고 싶다면


    val WriteActivity = activity as? WriteActivity
    val date = WriteActivity?.selectedDate
    val year = WriteActivity?.selectedYear
    val month = WriteActivity?.selectedMonth

    를 통해 접근 가능합니다.
    */

    fun moveToWriteFragment(pos: Int) {
        val fragment = WriteFragment().apply {
            arguments = Bundle().apply {
                putInt("selectedThemeIndex", pos)
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.write_activity_lo, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }



}


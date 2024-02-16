package com.example.a_write

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.Button
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.adapter.ThemeAdapter


class ThemeFragment : Fragment() {
    lateinit var rvThemes: RecyclerView
    lateinit var mAdapter: ThemeAdapter
    lateinit var btnToWriteFragment: Button
    val themes = arrayOf("theme_blue", "theme_green", "theme_yellow","theme_red")
    var selectedThemeIndex: Int? = null  // 선택한 테마의 인덱스를 저장할 변수 추가

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_theme, container, false)
        rvThemes = v.findViewById(R.id.rvThemes)
        btnToWriteFragment = v.findViewById(R.id.btnToWriteFragment)
        rvThemes.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvThemes.layoutManager = layoutManager

        val dataThemes = arrayListOf(themes[0],themes[1],themes[2],themes[3]) //테마리스트

        mAdapter = ThemeAdapter(dataThemes, requireActivity()) { position ->
            // 아이템 클릭 이벤트 처리
            btnToWriteFragment.isEnabled = true
            selectedThemeIndex = position
        }
        rvThemes.adapter = mAdapter
        rvThemes.setPadding(130,100,130,100)

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvThemes)

        Handler().postDelayed({
            val viewHolder = rvThemes.findViewHolderForAdapterPosition(0)
            val rl1 = viewHolder?.itemView?.findViewById<RelativeLayout>(R.id.rl1)
            rl1?.animate()?.scaleY(1F)?.scaleX(1F)?.duration = 350L
            rl1?.animate()?.setInterpolator(AccelerateInterpolator())?.start()
        }, 100L)
        rvThemes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val v = snapHelper.findSnapView(layoutManager)
                val pos = layoutManager.getPosition(v!!)
                // 'pos' is the index of the center item
                selectedThemeIndex = pos

                val viewHolder = rvThemes.findViewHolderForAdapterPosition(pos)
                val rl1 = viewHolder?.itemView?.findViewById<RelativeLayout>(R.id.rl1)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    rl1?.animate()?.setDuration(350)?.scaleX(1F)?.scaleY(1F)?.setInterpolator(AccelerateInterpolator())?.start()
                } else {
                    rl1?.animate()?.setDuration(350)?.scaleX(0.75F)?.scaleY(0.75F)?.setInterpolator(AccelerateInterpolator())?.start()
                }
            }
        })

        //button처리 필요

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}

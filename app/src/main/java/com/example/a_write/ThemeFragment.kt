package com.example.a_write

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.a_write.adapter.ThemeAdapter

class ThemeFragment : Fragment() {
    lateinit var rvThemes: RecyclerView
    lateinit var mAdapter: ThemeAdapter
    val themes = arrayOf("theme1", "theme2", "theme3")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_theme, container, false)
        rvThemes = v.findViewById(R.id.rvThemes)
        rvThemes.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rvThemes.layoutManager = layoutManager

        val dataThemes = arrayListOf(themes[0],themes[1],themes[2])

        mAdapter = ThemeAdapter(dataThemes, requireActivity())
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
                val viewHolder = rvThemes.findViewHolderForAdapterPosition(pos)
                val rl1 = viewHolder?.itemView?.findViewById<RelativeLayout>(R.id.rl1)

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    rl1?.animate()?.setDuration(350)?.scaleX(1F)?.scaleY(1F)?.setInterpolator(AccelerateInterpolator())?.start()
                } else {
                    rl1?.animate()?.setDuration(350)?.scaleX(0.75F)?.scaleY(0.75F)?.setInterpolator(AccelerateInterpolator())?.start()
                }
            }
        })
        return v
    }
}
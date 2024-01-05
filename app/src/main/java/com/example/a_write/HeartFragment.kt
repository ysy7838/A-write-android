package com.example.a_write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a_write.databinding.FragmentHeartBinding

class HeartFragment : Fragment() {

    private lateinit var binding: FragmentHeartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeartBinding.inflate(inflater, container, false)
        return binding.root
    }

}
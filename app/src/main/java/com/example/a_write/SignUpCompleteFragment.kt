package com.example.a_write

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentSignupCompleteBinding

class SignUpCompleteFragment : Fragment() {
    lateinit var binding : FragmentSignupCompleteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupCompleteBinding.inflate(inflater, container, false)

        binding.completeButtonTv.setOnClickListener{
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        return binding.root
    }
}
package com.example.a_write

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a_write.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    private lateinit var binding: FragmentWriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(index: Int): WriteFragment {
            val fragment = WriteFragment()
            val args = Bundle()
            args.putInt("index", index)
            fragment.arguments = args
            return fragment
        }
    }
    val index = arguments?.getInt("index")
}
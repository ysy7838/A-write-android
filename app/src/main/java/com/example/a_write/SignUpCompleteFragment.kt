package com.example.a_write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentSignupCompleteBinding

class SignUpCompleteFragment : Fragment() , SignUpView{
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






        //activity로부터 데이터 가져올 것
//            authService.signUp(getUser())




        return binding.root
    }

    override fun onSignUpSuccess() {
        Log.d("SIGNUP", "Success")

        //실사용 땐 고치기
        Toast.makeText(activity, "signup 성공", Toast.LENGTH_SHORT).show()
    }



    override fun onSignUpFailure(message: String) {
        Log.d("SIGNUP", "fail")
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
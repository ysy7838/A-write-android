package com.example.a_write

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a_write.databinding.ActivitySignupBinding


class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        start()

    }


    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun start(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.signup_frm, SignUpFragment())
            .commitAllowingStateLoss()
    }
}

package com.example.a_write

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a_write.databinding.ActivityResetBinding

class ResetActivity : AppCompatActivity() {

    lateinit var binding: ActivityResetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetBinding.inflate(layoutInflater)
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
            .replace(R.id.reset_frm,ResetSendFragment())
            .commitAllowingStateLoss()
    }
}

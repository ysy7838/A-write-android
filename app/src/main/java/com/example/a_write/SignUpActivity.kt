package com.example.a_write

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a_write.databinding.ActivitySignupBinding


class SignUpActivity : AppCompatActivity() {

    lateinit var user:User

    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        start()

    }


    private fun start(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.signup_frm, SignUpFragment())
            .commitAllowingStateLoss()
    }

    fun setUserData(user:User) {
        this.user = user
    }


    /* 다른 fragment에서 date, month, year접근하고 싶다면


    val WriteActivity = activity as? WriteActivity
    val date = WriteActivity?.selectedDate
    val year = WriteActivity?.selectedYear
    val month = WriteActivity?.selectedMonth

    를 통해 접근 가능합니다.
    */
}

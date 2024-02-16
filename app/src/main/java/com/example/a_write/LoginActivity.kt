package com.example.a_write

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a_write.api.AuthService
import com.example.a_write.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginView {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginPwTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.loginSignTv.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        button()

    }

    private fun button(){
        binding.loginBtn.setOnClickListener {

            //시연용
            //startMainActivity()

            //login
            val authService = AuthService()
            authService.setLoginView(this)

            authService.login(getUser())

        }

        binding.loginSignTv.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.loginPwTv.setOnClickListener{
            val intent = Intent(this,ResetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        Log.d("SIGNUP/SUCCESS", "")
    }


    override fun onLoginSuccess() {
        startMainActivity()
    }

    override fun onLoginFailure(message:String) {
        Log.d("SIGNUP", "fail")
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }

    override fun onSessionIdReceived(sessionId: String?) {
        sessionId?.let {
            val spf = getSharedPreferences("auth2" , MODE_PRIVATE)
            val editor = spf.edit()
            editor.putString("Session", sessionId)
            editor.apply()
        }
    }

    private fun getUser(): UserLogin {

        val id :String =  binding.idInput.text.toString()
        val password :String =  binding.passwordInput.text.toString()

        return UserLogin(id, password)
    }
}
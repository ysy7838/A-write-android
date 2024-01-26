package com.example.a_write

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class login : AppCompatActivity() {

    lateinit var idInput : EditText
    lateinit var passwordInput : EditText
    lateinit var loginBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        idInput = findViewById(R.id.id_input)
        passwordInput = findViewById(R.id.password_input)
        loginBtn = findViewById(R.id.login_btn)

        loginBtn.setOnClickListener {
            val id = idInput.text.toString()
            val password = passwordInput.text.toString()
            Log.i("Test", "id: $id and password : $password")
        }
    }
}
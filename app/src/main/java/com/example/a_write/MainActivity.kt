package com.example.a_write

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a_write.api.DiaryService
import com.example.a_write.api.MyPageService
import com.example.a_write.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var diaryService: DiaryService
    private lateinit var myPageService: MyPageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        diaryService = DiaryService(applicationContext)
        myPageService = MyPageService(applicationContext)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment(diaryService))
            .commitAllowingStateLoss()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.main_bnv)

        //초기 선택 항목을 2번째 항목으로
        bottomNavigationView.selectedItemId = R.id.homeFragment

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment(diaryService))
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.writeFragment -> {
                    val intent = Intent(this, WriteActivity::class.java)
                    startActivity(intent)
                    return@setOnItemSelectedListener true
                }

                R.id.heartFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HeartFragment(diaryService))
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.profileFragment -> {
                    val profileFragment = ProfileFragment(myPageService)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, profileFragment)
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}
package com.example.a_write

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen()

        // Showing the React Native splash screen
//        androidx.core.splashscreen.SplashScreen.installSplashScreen(this);
//        org.devio.rn.splashscreen.SplashScreen.show(this, true); // custom splash screen from react-native-splash-screen library

        // Call to the superclass' onCreate method
        super.onCreate(null)

//        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        val motionLayout = findViewById<MotionLayout>(R.id.motionLayout)
        motionLayout.addTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }


        })
    }

}
package com.example.movieapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val background: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    val intent=Intent(applicationContext,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                }
            }
        }
        background.start()
    }
}
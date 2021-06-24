package com.example.movieapp

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movieapp.broadcast.NetworkChangeListener

class MainActivity : AppCompatActivity() {
    private lateinit var networkChangeListener:NetworkChangeListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        networkChangeListener= NetworkChangeListener()
    }

    override fun onStart() {
        val intentFilter=IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeListener,intentFilter)
        super.onStart()
    }
    override fun onStop() {
        unregisterReceiver(networkChangeListener)
        super.onStop()
    }
}
package com.example.lab2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class AppCompatActivityWithLog : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("App Logger","[${javaClass.name}]: Created")
    }

    override fun onPause() {
        super.onPause()
        Log.i("App Logger","[${javaClass.name}]: Paused")
    }

    override fun onResume() {
        super.onResume()
        Log.i("App Logger","[${javaClass.name}]: Resumed")
    }

    override fun onStart() {
        super.onStart()
        Log.i("App Logger","[${javaClass.name}]: Started")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("App Logger","[${javaClass.name}]: Restarted")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("App Logger","[${javaClass.name}]: Destroyed")
    }

    override fun onStop() {
        super.onStop()
        Log.i("App Logger","[${javaClass.name}]: Stopped")
    }
}
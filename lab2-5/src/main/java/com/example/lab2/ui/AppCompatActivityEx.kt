package com.example.lab2.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lab2.common.DatabaseHelper

open class AppCompatActivityEx : AppCompatActivity(){
    protected lateinit var dbHelper : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = DatabaseHelper(this)
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

    fun showToast(resourceId: Int){
        Toast.makeText(
            this,
            resources.getString(resourceId),
            Toast.LENGTH_SHORT
        ).show()
    }
}
package com.example.lab2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivityWithLog() {
    private fun isLoggedIn() : Boolean{
        return false
        //TODO
//        val sharedPref = this.getSharedPreferences(Globals.APP_SETTINGS_PREFERENCE_KEY,0)
//        if (!sharedPref.contains("is_logged_in")){
//            return false
//        }
//        return try {
//            sharedPref.getBoolean("is_logged_in",false)
//        }catch (ex : java.lang.ClassCastException){
//            false
//        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isLoggedIn()){
            val intent = Intent(this, LoginActivity::class.java).apply {}
            startActivity(intent)
            return
        }
        val intent = Intent(this, ContentListActivity::class.java).apply {}
        startActivity(intent)
    }

}
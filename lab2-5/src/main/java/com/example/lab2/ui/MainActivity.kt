package com.example.lab2.ui

import android.content.Intent
import android.os.Bundle

class MainActivity : AppCompatActivityEx() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, LoginActivity::class.java).apply {}
        startActivity(intent)
    }

}
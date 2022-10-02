package com.example.lab2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

const val EXTRA_MESSAGE = "com.example.lab2.ContentListActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Globals.contentListData.add("Sample")
    }

    fun btnLoginOnClick(view: View){
        val login = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        if (login.text.isEmpty() or password.text.isEmpty()){
            Toast.makeText(
                baseContext,
                "Enter login and password",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val intent = Intent(this, ContentListActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, login.text)
        }
        startActivity(intent)
    }

}
package com.example.lab2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivityWithLog() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btnLogin).setOnClickListener{
            // Check if fields are not empty
            val login = findViewById<EditText>(R.id.editTextTextEmailAddress)
            val loginStr = login.text.toString()
            val password = findViewById<EditText>(R.id.editTextTextPassword)
            if (loginStr.isEmpty() or password.text.isEmpty()){
                Toast.makeText(
                    baseContext,
                    "Enter login and password",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Save login
            val sharedPref = this.getSharedPreferences(Globals.APP_SETTINGS_PREFERENCE_KEY,0)
            val editor = sharedPref.edit()
            editor.putBoolean("is_logged_in",true)
            if (!editor.commit()){
                Toast.makeText(
                    baseContext,
                    "Error occurred while logging in. Try later.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Start ContentListActivity
            val intent = Intent(this, ContentListActivity::class.java).apply {
                putExtra(Globals.EXTRA_MESSAGE_ON_CREATE_MAIN_ACT, loginStr)
            }
            startActivity(intent)
        }
    }

}
package com.example.lab2.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import com.example.lab2.common.Globals
import com.example.lab2.common.UserName
import com.example.lab2.R


class LoginActivity : AppCompatActivityEx(),OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.btnLogin).setOnClickListener(this)
        findViewById<Button>(R.id.btnSignIn).setOnClickListener(this)
        // dbHelper.deleteAllUserRecords()
        // dbHelper.dropDataBase()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun startContentListActivity(login: String){
        val intent = Intent(this, ContentListActivity::class.java).apply {}
        startActivity(intent)
    }

    private fun getUserDataEntered() : UserName.Raw {
        val login = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val loginStr = login.text.toString()
        val password = findViewById<EditText>(R.id.editTextTextPassword)
        val passStr = password.text.toString()
        return UserName.Raw(loginStr,passStr)
    }

    private fun loginOnClick(){
        // Check if fields are not empty
        val user = getUserDataEntered()
        if (user.login.isEmpty() or user.pass.isEmpty()){
            showToast(R.string.txt_login_or_password_invalid)
            return
        }
        val existingUser = dbHelper.findUserWithUserName(user.login)
        if (existingUser == null){
            showToast(R.string.txt_not_existing_user)
            return
        }
        val enteredUserEncoded = user.toEncoded()

        if (!enteredUserEncoded.passHash.contentEquals(existingUser.passHash)){
            showToast(R.string.txt_invalid_password)
            return
        }
        showToast(R.string.txt_authorisation_success)
        Globals.instance.loggedInUserName = user.login
        startContentListActivity(user.login)
    }

    private fun signInOnClick(){
        val userData = getUserDataEntered()
        val existingUser = dbHelper.findUserWithUserName(userData.login)
        if (existingUser != null){
            showToast(R.string.txt_user_already_exists)
            return
        }
        val userName = UserName.Raw(userData.login,userData.pass).toEncoded()
        dbHelper.addUser(userName)
        showToast(R.string.txt_authorisation_success)
        startContentListActivity(userData.login)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnLogin ->
                loginOnClick()

            R.id.btnSignIn ->
                signInOnClick()

            else -> {}
        }

    }


}
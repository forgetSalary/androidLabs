package com.example.lab2.ui

import android.content.Intent
import com.example.lab2.R
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import com.example.lab2.common.Globals
import com.example.lab2.common.UserName

class SettingsActivity : AppCompatActivityEx(),OnClickListener {
    private lateinit var oldPassHash : ByteArray

    private fun construct(){
        val currentUser = dbHelper.findUserWithUserName(
            Globals.instance.loggedInUserName) ?: throw Exception("Database error")
        oldPassHash = currentUser.passHash
    }

    private fun initComponents(){
        findViewById<Button>(R.id.btnCancle).setOnClickListener(this)
        findViewById<Button>(R.id.btnSave).setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        construct()
        setContentView(R.layout.activity_settings)
        initComponents()
    }
    private fun startContentListActivity(){
        val intent = Intent(this, ContentListActivity::class.java).apply {}
        startActivity(intent)
    }

    private fun doPasswordsMatch() : Boolean{
        val newPass1 = findViewById<EditText>(R.id.editTextTextPasswordNew).text.toString()
        val newPass2 = findViewById<EditText>(R.id.editTextTextPasswordRepeate).text.toString()
        return newPass1 == newPass2
    }

    private fun getEnteredNewPassword() : String{
        val editTextNewPass = findViewById<EditText>(R.id.editTextTextPasswordNew)
        return editTextNewPass.text.toString()
    }

    private fun getEnteredOldPassword() : String{
        val editTextOldPass = findViewById<EditText>(R.id.editTextTextPasswordOld)
        return editTextOldPass.text.toString()
    }

    private fun saveOnClick(){
        if (!doPasswordsMatch()){
            showToast(R.string.txt_passwords_do_not_match)
            return
        }

        if (!oldPassHash.contentEquals(UserName.hashPassword(getEnteredOldPassword()))){
            showToast(R.string.txt_invalid_password)
            return
        }

        val newUserName = UserName.Raw(
            Globals.instance.loggedInUserName,
            getEnteredNewPassword()
        ).toEncoded()

        dbHelper.changeUserPasswordHash(newUserName)

        finish()
    }

    private fun cancelOnClick(){
        //startContentListActivity()
        finish()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.btnSave ->
                saveOnClick()

            R.id.btnCancle ->
                cancelOnClick()

            else -> {}
        }
    }
}
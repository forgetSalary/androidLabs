package com.example.lab2.common

import android.app.Application

class Globals : Application() {
    var loggedInUserName : String = ""

    companion object {
        private val mInstance = Globals()
        val instance get() = mInstance

        const val APP_SETTINGS_PREFERENCE_KEY = "CommonSetting"
        const val EXTRA_MESSAGE_ON_CREATE_MAIN_ACT = "com.example.lab2.Activities.MainActivity"
    }
}
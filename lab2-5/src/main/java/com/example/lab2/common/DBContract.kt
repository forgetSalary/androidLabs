package com.example.lab2.common

import android.provider.BaseColumns

class DBContract private constructor() {
    object UserEntry : BaseColumns {
        const val TABLE_NAME = "users"
        const val COLUMN_NAME_LOGIN = "login"
        const val COLUMN_NAME_PASS = "pass"
        const val COLUMN_CONTENT_LIST = "content_list"
    }
    enum class UserColumns{
        LOGIN,
        PASS_HASH,
        CONTENT_LIST,
    }
}

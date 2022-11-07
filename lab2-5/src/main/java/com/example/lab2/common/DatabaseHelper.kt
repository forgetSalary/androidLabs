package com.example.lab2.common

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.hardware.SensorAdditionalInfo

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createUserTable = (
                "CREATE TABLE ${DBContract.UserEntry.TABLE_NAME} ( " +
                        "${DBContract.UserEntry.COLUMN_NAME_LOGIN} TEXT," +
                        "${DBContract.UserEntry.COLUMN_NAME_PASS} BINARY(${UserName.Encoded.PASS_HASH_LEN}), " +
                        "${DBContract.UserEntry.COLUMN_CONTENT_LIST} TEXT)")
        db.execSQL(createUserTable)
    }

    fun dropDataBase(db : SQLiteDatabase = writableDatabase){
        db.execSQL("DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropDataBase(db)
        onCreate(db)
    }

    fun addUser(userName: UserName.Encoded, contentListText:String = "") {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_NAME_LOGIN, userName.login)
        values.put(DBContract.UserEntry.COLUMN_NAME_PASS, userName.passHash)
        values.put(DBContract.UserEntry.COLUMN_CONTENT_LIST, contentListText)
        db.insert(DBContract.UserEntry.TABLE_NAME, null, values)
        db.close()
    }

    fun setUserContentList(userName: String,contentListText: String){
        val selectQuery =
                    "update ${DBContract.UserEntry.TABLE_NAME} " +
                    "set ${DBContract.UserEntry.COLUMN_CONTENT_LIST}='$contentListText' " +
                            "where ${DBContract.UserEntry.COLUMN_NAME_LOGIN}='$userName'"
        val db = this.writableDatabase
        db.execSQL(selectQuery)
    }

    fun getUserContentListText(userName: String) : String?{
        val tableNameKey = DBContract.UserEntry.TABLE_NAME
        val userNameKey = DBContract.UserEntry.COLUMN_NAME_LOGIN
        val selectQuery = "SELECT * FROM $tableNameKey WHERE $userNameKey='$userName';"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var contentListText: String? = null
        if (cursor.moveToFirst()){
            contentListText = cursor.getString(DBContract.UserColumns.CONTENT_LIST.ordinal)
        }
        cursor.close();
        return contentListText
    }

    fun changeUserPasswordHash(userName: UserName.Encoded){
        val db = this.writableDatabase
        val contentList = getUserContentListText(userName.login)
        deleteUserWithUserName(userName.login)
        addUser(userName)
        if (contentList != null) {
            setUserContentList(userName.login,contentList)
        }
        db.close()
    }

    fun getAllUsers(): List<UserName.Encoded> {
        val usersList: MutableList<UserName.Encoded> = ArrayList()
        val selectQuery = "SELECT * FROM ${DBContract.UserEntry.TABLE_NAME}"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val userName = UserName.Encoded(
                    0,
                    cursor.getString(0),
                    cursor.getBlob(1)
                )
                usersList.add(userName)
            }while (cursor.moveToNext())
        }
        cursor.close()
        return usersList
    }

    fun findUserWithUserName(userName: String) : UserName.Encoded?{
        val tableNameKey = DBContract.UserEntry.TABLE_NAME
        val userNameKey = DBContract.UserEntry.COLUMN_NAME_LOGIN
        val selectQuery = "SELECT * FROM $tableNameKey WHERE $userNameKey='$userName';"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var existingUserName: UserName.Encoded? = null
        if (cursor.moveToFirst()){
            existingUserName = UserName.Encoded(
                0,
                cursor.getString(DBContract.UserColumns.LOGIN.ordinal),
                cursor.getBlob(DBContract.UserColumns.PASS_HASH.ordinal)
            )
        }
        cursor.close();
        return existingUserName
    }

    fun deleteAllUserRecords() {
        val db = this.writableDatabase
        db.execSQL("delete from ${DBContract.UserEntry.TABLE_NAME}");
    }

    fun deleteUserWithUserName(userName: String){
        val db = this.writableDatabase
        db.execSQL(
            "delete from ${DBContract.UserEntry.TABLE_NAME} " +
                "where ${DBContract.UserEntry.COLUMN_NAME_LOGIN}='$userName'");
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Users.db"
    }
}

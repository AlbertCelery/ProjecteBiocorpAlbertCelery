package com.example.projectebiocorpalbertcelery.data

import android.content.ContentValues

class LoginDatabaseManager: DatabaseManager() {
    fun userPasswordExists(username: String, password: String): Boolean {
        loadDatabase()
        val cursor = db!!.rawQuery("SELECT * FROM user WHERE Username = ? AND Password = ?", arrayOf(username, password))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    fun saveUserPass(username: String, password: String): Boolean {
        loadDatabase()
        val values = ContentValues()
        values.put("Username", username)
        values.put("Password", password)
        val result = db!!.insert("user", null, values)
        return result != -1L
    }
}
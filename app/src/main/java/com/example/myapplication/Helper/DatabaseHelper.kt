package com.example.myapplication.Helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 2
        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_USERNAME = "username"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PROFILE_NAME = "profile_name"
        const val COLUMN_PROFILE_PICTURE = "profile_picture"
        const val COLUMN_FINGERPRINT_ENABLED = "fingerprint_enabled"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USERNAME TEXT, " +
                "$COLUMN_EMAIL TEXT, " +
                "$COLUMN_PASSWORD TEXT, " +
                "$COLUMN_PROFILE_NAME TEXT, " +
                "$COLUMN_PROFILE_PICTURE BLOB, " + // <-- Add a comma here
                "$COLUMN_FINGERPRINT_ENABLED INTEGER DEFAULT 0)")
        db?.execSQL(createTable)
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(username: String, email: String, password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_EMAIL, email)
            put(COLUMN_PASSWORD, password)
        }
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun checkUserExists(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME=?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun updateUserProfile(username: String, profileName: String, profilePicture: Bitmap): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_PROFILE_NAME, profileName)

            val stream = ByteArrayOutputStream()
            profilePicture.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val profilePictureBytes = stream.toByteArray()
            put(COLUMN_PROFILE_PICTURE, profilePictureBytes)
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_USERNAME=?", arrayOf(username))
    }

    fun getUserProfile(username: String): Pair<String?, ByteArray?>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_PROFILE_NAME, $COLUMN_PROFILE_PICTURE FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ?", arrayOf(username))

        var profileData: Pair<String?, ByteArray?>? = null
        if (cursor.moveToFirst()) {
            val profileName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_NAME))
            val profilePicture = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_PROFILE_PICTURE))
            profileData = Pair(profileName, profilePicture)
        }
        cursor.close()
        return profileData
    }

    fun validateUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_USERNAME=? AND $COLUMN_PASSWORD=?", arrayOf(username, password))
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }

    fun setFingerprintEnabled(username: String, enabled: Boolean): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_FINGERPRINT_ENABLED, if (enabled) 1 else 0)
        }
        return db.update(TABLE_NAME, contentValues, "$COLUMN_USERNAME=?", arrayOf(username))
    }

    fun isFingerprintEnabled(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_FINGERPRINT_ENABLED FROM $TABLE_NAME WHERE $COLUMN_USERNAME = ?", arrayOf(username))
        var enabled = false
        if (cursor.moveToFirst()) {
            enabled = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FINGERPRINT_ENABLED)) == 1
        }
        cursor.close()
        return enabled
    }


}





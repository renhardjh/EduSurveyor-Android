package com.group4.edusurveyor.repository.local.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.group4.edusurveyor.repository.local.contract.UserContract.UserColums

class UserDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${UserColums.TABLE_NAME}" +
            " (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " ${UserColums.COLUMN_EMAIL} TEXT," +
            " ${UserColums.COLUMN_NAME_NAME} TEXT," +
            " ${UserColums.COLUMN_NAME_TOKEN} TEXT," +
            " ${UserColums.COLUMN_NAME_CREATEDAT} TEXT," +
            " ${UserColums.COLUMN_NAME_UPDATEDDAT} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $UserColums.TABLE_NAME"

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "UserRecord.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
}
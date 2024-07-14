package com.group4.edusurveyor.repository.local.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.group4.edusurveyor.repository.local.contract.SurveyContract.SurveyColums

class SurveyDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_ENTRIES = "CREATE TABLE ${SurveyColums.TABLE_NAME}" +
            " (${BaseColumns._ID} INTEGER PRIMARY KEY," +
            " ${SurveyColums.COLUMN_NAME_TITLE} TEXT," +
            " ${SurveyColums.COLUMN_NAME_FULLNAME} TEXT," +
            " ${SurveyColums.COLUMN_NAME_GENDER} TEXT," +
            " ${SurveyColums.COLUMN_NAME_AGE} INT," +
            " ${SurveyColums.COLUMN_NAME_UNIVERSITY} TEXT," +
            " ${SurveyColums.COLUMN_NAME_MAJOR} TEXT," +
            " ${SurveyColums.COLUMN_NAME_JOB} TEXT," +
            " ${SurveyColums.COLUMN_NAME_CREATEDAT} TEXT," +
            " ${SurveyColums.COLUMN_NAME_UPDATEDDAT} TEXT)"

    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $SurveyColums.TABLE_NAME"

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "SurveyRecord.db"
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
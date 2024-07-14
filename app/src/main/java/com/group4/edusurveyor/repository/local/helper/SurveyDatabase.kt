package com.group4.edusurveyor.repository.local.helper

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.group4.edusurveyor.module.main.model.SurveyRecordModel
import com.group4.edusurveyor.repository.local.contract.SurveyContract.SurveyColums

abstract class DatabaseSQLite {
    abstract var mDbHelper: SurveyDBHelper

    abstract fun insertData(
            model: SurveyRecordModel
        ): Long

    abstract fun updateData(
        model: SurveyRecordModel
    ): Int

    abstract fun deleteData(
        title: String
    )

    abstract fun getData(): List<SurveyRecordModel>
}

class SurveyDatabase(context: Context): DatabaseSQLite() {
    override var mDbHelper = SurveyDBHelper(context)

    override fun insertData(
        model: SurveyRecordModel
    ): Long {
        val db = mDbHelper.writableDatabase

        val values = ContentValues()
        values.put(SurveyColums.COLUMN_NAME_TITLE, model.title)
        values.put(SurveyColums.COLUMN_NAME_FULLNAME, model.fullName)
        values.put(SurveyColums.COLUMN_NAME_GENDER, model.gender)
        values.put(SurveyColums.COLUMN_NAME_AGE, model.age)
        values.put(SurveyColums.COLUMN_NAME_UNIVERSITY, model.university)
        values.put(SurveyColums.COLUMN_NAME_MAJOR, model.major)
        values.put(SurveyColums.COLUMN_NAME_JOB, model.job)
        values.put(SurveyColums.COLUMN_NAME_CREATEDAT, model.createAt)
        values.put(SurveyColums.COLUMN_NAME_UPDATEDDAT, model.updateAt)

        val newRowId = db.insert(SurveyColums.TABLE_NAME, null, values)
        return newRowId
    }

    override fun updateData(
        model: SurveyRecordModel
    ): Int {
        var db = mDbHelper.writableDatabase
        val values =  ContentValues()
        values.put(SurveyColums.COLUMN_NAME_FULLNAME, model.fullName)
        values.put(SurveyColums.COLUMN_NAME_GENDER, model.gender)
        values.put(SurveyColums.COLUMN_NAME_AGE, model.age)
        values.put(SurveyColums.COLUMN_NAME_UNIVERSITY, model.university)
        values.put(SurveyColums.COLUMN_NAME_MAJOR, model.major)
        values.put(SurveyColums.COLUMN_NAME_JOB, model.job)
        values.put(SurveyColums.COLUMN_NAME_CREATEDAT, model.createAt)
        values.put(SurveyColums.COLUMN_NAME_UPDATEDDAT, model.updateAt)

        val selection = BaseColumns._ID + " = ?"
        val selectionArgs = arrayOf(model.id.toString())

        val count = db.update(
            SurveyColums.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )

        return count
    }

    override fun deleteData(id: String) {
        var db = mDbHelper.writableDatabase
        val selection = BaseColumns._ID + " LIKE ?"
        val selectionArgs = arrayOf(id)
        db.delete(SurveyColums.TABLE_NAME, selection, selectionArgs)
    }

    override fun getData(): List<SurveyRecordModel> {
        var db = mDbHelper.readableDatabase
        var listData = mutableListOf<SurveyRecordModel>()

        var projection = arrayOf(
            BaseColumns._ID,
            SurveyColums.COLUMN_NAME_TITLE,
            SurveyColums.COLUMN_NAME_FULLNAME,
            SurveyColums.COLUMN_NAME_GENDER,
            SurveyColums.COLUMN_NAME_AGE,
            SurveyColums.COLUMN_NAME_UNIVERSITY,
            SurveyColums.COLUMN_NAME_MAJOR,
            SurveyColums.COLUMN_NAME_JOB,
            SurveyColums.COLUMN_NAME_CREATEDAT,
            SurveyColums.COLUMN_NAME_UPDATEDDAT
        )

        var sortOrder = SurveyColums.COLUMN_NAME_TITLE + " DESC"

        var cursor = db.query(
            SurveyColums.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        cursor.moveToFirst()

        if (cursor.count>0) {
            do {
                val id = cursor.getColumnIndex(BaseColumns._ID)
                val title = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_TITLE)
                val fullname = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_FULLNAME)
                val gender = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_GENDER)
                val age = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_AGE)
                val university = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_UNIVERSITY)
                val major = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_MAJOR)
                val job = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_JOB)
                val createdAt = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_CREATEDAT)
                val updatedAt = cursor.getColumnIndex(SurveyColums.COLUMN_NAME_UPDATEDDAT)

                val model = SurveyRecordModel(
                    cursor.getInt(id),
                    cursor.getString(title),
                    cursor.getString(fullname),
                    cursor.getString(gender),
                    cursor.getInt(age),
                    cursor.getString(university),
                    cursor.getString(major),
                    cursor.getString(job),
                    cursor.getString(createdAt),
                    cursor.getString(updatedAt)
                )

                listData.add(model)
                cursor.moveToNext()
            } while (!cursor.isAfterLast)
        }

        return listData
    }
}
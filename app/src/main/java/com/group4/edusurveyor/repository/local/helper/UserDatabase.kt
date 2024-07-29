package com.group4.edusurveyor.repository.local.helper

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.group4.edusurveyor.module.auth.model.UserRecordModel
import com.group4.edusurveyor.repository.local.contract.UserContract.UserColums

abstract class UserDatabaseSQLite {
    abstract var mDbHelper: UserDBHelper

    abstract fun insertData(
            model: UserRecordModel
        ): Long

    abstract fun updateData(
        model: UserRecordModel
    ): Int

    abstract fun deleteData(
        title: String
    )

    abstract fun getData(): List<UserRecordModel>
}

class UserDatabase(context: Context): UserDatabaseSQLite() {
    override var mDbHelper = UserDBHelper(context)

    override fun insertData(
        model: UserRecordModel
    ): Long {
        val db = mDbHelper.writableDatabase

        val values = ContentValues()
        values.put(UserColums.COLUMN_EMAIL, model.email)
        values.put(UserColums.COLUMN_NAME_NAME, model.name)
        values.put(UserColums.COLUMN_NAME_TOKEN, model.token)
        values.put(UserColums.COLUMN_NAME_CREATEDAT, model.createAt)
        values.put(UserColums.COLUMN_NAME_UPDATEDDAT, model.updateAt)

        val newRowId = db.insert(UserColums.TABLE_NAME, null, values)
        return newRowId
    }

    override fun updateData(
        model: UserRecordModel
    ): Int {
        var db = mDbHelper.writableDatabase
        val values =  ContentValues()
        values.put(UserColums.COLUMN_EMAIL, model.email)
        values.put(UserColums.COLUMN_NAME_NAME, model.name)
        values.put(UserColums.COLUMN_NAME_TOKEN, model.token)
        values.put(UserColums.COLUMN_NAME_CREATEDAT, model.createAt)
        values.put(UserColums.COLUMN_NAME_UPDATEDDAT, model.updateAt)

        val selection = BaseColumns._ID + " = ?"
        val selectionArgs = arrayOf(model.id.toString())

        val count = db.update(
            UserColums.TABLE_NAME,
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
        db.delete(UserColums.TABLE_NAME, selection, selectionArgs)
    }

    override fun getData(): List<UserRecordModel> {
        var db = mDbHelper.readableDatabase
        var listData = mutableListOf<UserRecordModel>()

        var projection = arrayOf(
            BaseColumns._ID,
            UserColums.COLUMN_EMAIL,
            UserColums.COLUMN_NAME_NAME,
            UserColums.COLUMN_NAME_TOKEN,
            UserColums.COLUMN_NAME_CREATEDAT,
            UserColums.COLUMN_NAME_UPDATEDDAT
        )

        var sortOrder = UserColums.COLUMN_NAME_TOKEN + " DESC"

        var cursor = db.query(
            UserColums.TABLE_NAME,
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
                val email = cursor.getColumnIndex(UserColums.COLUMN_EMAIL)
                val name = cursor.getColumnIndex(UserColums.COLUMN_NAME_NAME)
                val token = cursor.getColumnIndex(UserColums.COLUMN_NAME_TOKEN)
                val createdAt = cursor.getColumnIndex(UserColums.COLUMN_NAME_CREATEDAT)
                val updatedAt = cursor.getColumnIndex(UserColums.COLUMN_NAME_UPDATEDDAT)

                val model = UserRecordModel(
                    cursor.getInt(id),
                    cursor.getString(email),
                    cursor.getString(name),
                    cursor.getString(token),
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
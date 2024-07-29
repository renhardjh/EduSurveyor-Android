package com.group4.edusurveyor.module.auth.viewmodel

import com.group4.edusurveyor.module.auth.model.UserRecordModel
import com.group4.edusurveyor.repository.local.helper.UserDatabaseSQLite
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LoginViewModel {

    var mDatabase: UserDatabaseSQLite
    var modelData: UserRecordModel? = null

    constructor(mDatabase: UserDatabaseSQLite) {
        this.mDatabase = mDatabase
    }

    fun insertNewUser(
        model: UserRecordModel
    ) {
        mDatabase.insertData(model)
    }

    fun getUserData(
        model: UserRecordModel
    ): List<UserRecordModel> {
        return mDatabase.getData()
    }

    fun getCurrentDateTime(): String {
        val c: Date = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate: String = df.format(c)
        return formattedDate
    }
}
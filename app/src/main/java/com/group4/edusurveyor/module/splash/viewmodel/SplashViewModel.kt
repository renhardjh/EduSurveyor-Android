package com.group4.edusurveyor.module.splash.viewmodel

import com.group4.edusurveyor.module.auth.model.UserRecordModel
import com.group4.edusurveyor.repository.local.helper.UserDatabaseSQLite
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SplashViewModel {

    var mDatabase: UserDatabaseSQLite

    constructor(mDatabase: UserDatabaseSQLite) {
        this.mDatabase = mDatabase
    }

    fun getUserData(): List<UserRecordModel> {
        return mDatabase.getData()
    }
}
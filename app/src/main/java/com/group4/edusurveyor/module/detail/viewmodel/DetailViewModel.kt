package com.group4.edusurveyor.module.detail.viewmodel

import com.group4.edusurveyor.module.main.model.SurveyRecordModel
import com.group4.edusurveyor.repository.local.helper.DatabaseSQLite
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DetailViewModel {

    var mDatabase: DatabaseSQLite
    var modelData: SurveyRecordModel? = null

    constructor(mDatabase: DatabaseSQLite) {
        this.mDatabase = mDatabase
    }

    fun insertNewData(
        model: SurveyRecordModel
    ) {
        mDatabase.insertData(model)
    }

    fun getCurrentDateTime(): String {
        val c: Date = Calendar.getInstance().getTime()
        println("Current time => $c")

        val df = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault())
        val formattedDate: String = df.format(c)
        return formattedDate
    }

    fun updateData(
        model: SurveyRecordModel
    ): Int {
        return mDatabase.updateData(model)
    }
}
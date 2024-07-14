package com.group4.edusurveyor.module.main.viewmodel

import com.group4.edusurveyor.module.main.model.SurveyRecordModel
import com.group4.edusurveyor.repository.local.helper.DatabaseSQLite


class MainViewModel {

    var mDatabase: DatabaseSQLite
    lateinit var listData: List<SurveyRecordModel>

    constructor(mDatabase: DatabaseSQLite) {
        this.mDatabase = mDatabase
    }

    fun getData() {
        listData = mDatabase.getData()
    }

    fun deleteData(id: String) {
        mDatabase.deleteData(id)
    }
}
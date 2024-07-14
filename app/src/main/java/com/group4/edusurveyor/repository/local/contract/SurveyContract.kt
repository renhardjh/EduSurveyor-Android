package com.group4.edusurveyor.repository.local.contract

import android.provider.BaseColumns

class SurveyContract {
    class SurveyColums : BaseColumns {
        companion object {
            val TABLE_NAME = "survey_record"
            val COLUMN_NAME_TITLE = "title"
            val COLUMN_NAME_FULLNAME = "fullname"
            val COLUMN_NAME_GENDER = "gender"
            val COLUMN_NAME_AGE = "age"
            val COLUMN_NAME_UNIVERSITY = "university"
            val COLUMN_NAME_MAJOR = "major"
            val COLUMN_NAME_JOB = "job"
            val COLUMN_NAME_CREATEDAT = "created_at"
            val COLUMN_NAME_UPDATEDDAT = "updated_at"
        }
    }
}
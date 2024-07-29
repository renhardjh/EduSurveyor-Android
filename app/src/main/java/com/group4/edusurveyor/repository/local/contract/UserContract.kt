package com.group4.edusurveyor.repository.local.contract

import android.provider.BaseColumns

class UserContract {
    class UserColums : BaseColumns {
        companion object {
            val TABLE_NAME = "user_record"
            val COLUMN_EMAIL = "email"
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_TOKEN = "token"
            val COLUMN_NAME_CREATEDAT = "created_at"
            val COLUMN_NAME_UPDATEDDAT = "updated_at"
        }
    }
}
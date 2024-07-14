package com.group4.edusurveyor.module.main.model

import java.io.Serializable

class SurveyRecordModel(
    val id: Int,
    val title: String,
    val fullName: String,
    val gender: String,
    val age: Int,
    val university: String,
    val major: String,
    val job: String,
    val createAt: String,
    var updateAt: String
): Serializable

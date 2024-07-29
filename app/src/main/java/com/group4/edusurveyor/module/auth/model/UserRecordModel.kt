package com.group4.edusurveyor.module.auth.model

import java.io.Serializable

class UserRecordModel(
    val id: Int,
    val email: String,
    val name: String,
    val token: String,
    val createAt: String,
    var updateAt: String
): Serializable

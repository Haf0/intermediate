package com.bangkit.intermediate.model.Login

data class UserSessionModel(
    val name: String,
    val token: String,
    val isLogin: Boolean
)

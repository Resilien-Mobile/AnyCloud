package com.baidaidai.regexpstorm.domain

data class RegexpError(
    val errorMessage: String,
    val errorCause: String,
    val errorCompanion: String,
)

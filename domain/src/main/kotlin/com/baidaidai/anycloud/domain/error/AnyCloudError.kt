package com.baidaidai.anycloud.domain.error

interface AnyCloudError {
    val errorMessage: String
    val errorCause: String
    val errorCompanion: String?
}
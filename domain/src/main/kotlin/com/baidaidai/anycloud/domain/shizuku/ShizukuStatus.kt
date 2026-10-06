package com.baidaidai.anycloud.domain.shizuku

enum class ShizukuStatus {
    UNAVAILABLE,   // Shizuku 不存在 / 不可用
    UNAUTHORIZED,  // Shizuku 存在，但未授权
    AUTHORIZED,    // Shizuku 存在，且已授权
}
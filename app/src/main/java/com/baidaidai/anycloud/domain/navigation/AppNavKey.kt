package com.baidaidai.anycloud.domain.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey

@Serializable
data object HomeScreenNavKey : AppNavKey

@Serializable
data object PowerCloudNavKey : AppNavKey

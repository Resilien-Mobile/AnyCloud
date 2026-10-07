package com.baidaidai.anycloud.ui.navigation.things

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavKey : NavKey

@Serializable
data object HomeScreenNavKey : AppNavKey

@Serializable
data object TaskCloudNavKey : AppNavKey

@Serializable
data object PowerCloudNavKey : AppNavKey

@Serializable
data object SettingScreenNavKey : AppNavKey

@Serializable
data object ClipBoardPilotNavKey : AppNavKey


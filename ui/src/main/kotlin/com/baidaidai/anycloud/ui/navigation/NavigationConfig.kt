package com.baidaidai.anycloud.ui.navigation

import com.baidaidai.anycloud.ui.navigation.things.AppNavKey

data class NavigationConfig(
    val destinationName: String,
    val destinationIcon: Int,
    val destinationNavKey: AppNavKey
)
package com.baidaidai.anycloud.ui.navigation.intelligent

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface PolicyManagerNavKey: NavKey {
}

@Serializable
data object PolicyManagerKey: PolicyManagerNavKey

@Serializable
data object PolicyGroupKey: PolicyManagerNavKey
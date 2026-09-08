package com.baidaidai.anycloud.ui.component.settingScreen

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

object SettingScreenNecessaryComponents {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun SettingScreenTopAppBar(){
        LargeFlexibleTopAppBar(
            title = {
                Text(
                    text = "Setting",
                    style = MaterialTheme.typography.displaySmall
                )
            },
            subtitle = {
                Text(
                    text = ""
                )
            }
        )
    }
}
package com.baidaidai.anycloud.ui.component.powerScreen

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

object PowerScreenNecessaryComponents {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun PowerScreenTopAppBar(){
        LargeFlexibleTopAppBar(
            title = {
                Text(
                    text = "Power Cloud",
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
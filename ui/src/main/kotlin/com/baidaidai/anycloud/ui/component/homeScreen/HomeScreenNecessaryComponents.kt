package com.baidaidai.anycloud.ui.component.homeScreen

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

object HomeScreenNecessaryComponents {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun HomeScreenTopAppBar(){
        LargeFlexibleTopAppBar(
            title = {
                Text(
                    text = "Any Cloud",
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
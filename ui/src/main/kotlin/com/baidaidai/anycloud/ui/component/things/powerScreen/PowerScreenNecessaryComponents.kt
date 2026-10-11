package com.baidaidai.anycloud.ui.component.things.powerScreen

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.theme.getTopAppBarIconButtonColors

object PowerScreenNecessaryComponents {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun PowerScreenTopAppBar(
        onNavigationButtonClick: () -> Unit = {}
    ){
        LargeFlexibleTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onNavigationButtonClick,
                    colors = getTopAppBarIconButtonColors()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.material_symbols_menu),
                        contentDescription = stringResource(R.string.power_screen_top_app_bar_menu_content_description),
                    )
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.power_screen_top_app_bar_title),
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

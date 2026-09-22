package com.baidaidai.anycloud.ui.component.homeScreen

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.theme.getTopAppBarIconButtonColors

object HomeScreenNecessaryComponents {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun HomeScreenTopAppBar(
        onNavigationButtonClick: ()-> Unit = {}
    ){
        LargeFlexibleTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onNavigationButtonClick,
                    colors = getTopAppBarIconButtonColors()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.material_symbols_menu),
                        contentDescription = "Menu",
                    )
                }
            },
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

@PreviewLightDark
@Composable
private fun _preview_() {
    HomeScreenNecessaryComponents.HomeScreenTopAppBar()
}
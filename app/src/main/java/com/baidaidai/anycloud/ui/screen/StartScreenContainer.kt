package com.baidaidai.anycloud.ui.screen

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.baidaidai.anycloud.ui.component.navigation.NavigationDrawer

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StartScreenContainer() {
    NavigationDrawer{
        Scaffold(
            topBar = {
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
        ) { innerPadding ->
            if (false){
                PowerScreen(innerPadding)
            }else{
                HomeScreen(innerPadding)
            }
        }
    }
}

package com.baidaidai.anycloud.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.baidaidai.anycloud.domain.navigation.HomeScreenNavKey
import com.baidaidai.anycloud.domain.navigation.PowerCloudNavKey
import com.baidaidai.anycloud.ui.component.navigation.NavigationDrawer

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StartScreenContainer() {

    val navigation = rememberNavBackStack(HomeScreenNavKey)
    val currentDestination = navigation.last() ?: HomeScreenNavKey

    NavigationDrawer(
        onNavigationClick = { navigationConfig ->
            navigation.add(navigationConfig.destinationNavKey)
        }
    ){
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

            NavDisplay(
                backStack = navigation,
                modifier = Modifier
                    .fillMaxSize(),
                onBack = {
                    navigation.removeLastOrNull()
                },
                entryProvider = entryProvider{
                    entry<HomeScreenNavKey> {
                        HomeScreen(innerPadding)
                    }

                    entry<PowerCloudNavKey> {
                        PowerScreen(innerPadding)
                    }
                }
            )

        }
    }
}

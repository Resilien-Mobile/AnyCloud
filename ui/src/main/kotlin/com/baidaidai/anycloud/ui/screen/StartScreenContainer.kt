package com.baidaidai.anycloud.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.baidaidai.anycloud.domain.navigation.HomeScreenNavKey
import com.baidaidai.anycloud.domain.navigation.PowerCloudNavKey
import com.baidaidai.anycloud.domain.navigation.SettingScreenNavKey
import com.baidaidai.anycloud.domain.navigation.TaskCloudNavKey
import com.baidaidai.anycloud.ui.component.homeScreen.HomeScreenNecessaryComponents
import com.baidaidai.anycloud.ui.component.navigation.NavigationDrawer
import com.baidaidai.anycloud.ui.component.powerScreen.PowerScreenNecessaryComponents
import com.baidaidai.anycloud.ui.component.taskScreen.TaskScreenNecessaryComponents
import com.baidaidai.anycloud.ui.viewmodel.NavigationViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StartScreenContainer() {

    val navigation = rememberNavBackStack(HomeScreenNavKey)
    val currentDestination = navigation.last() ?: HomeScreenNavKey

    val navigationViewModel = hiltViewModel<NavigationViewModel>()

    NavigationDrawer(
        navigationViewModel = navigationViewModel,
        onNavigationClick = { navigationConfig ->
            navigation.removeLastOrNull()
            navigation.add(navigationConfig.destinationNavKey)
        }
    ){
        Scaffold(
            topBar = {
                when(currentDestination){
                    is HomeScreenNavKey -> HomeScreenNecessaryComponents.HomeScreenTopAppBar()
                    is TaskCloudNavKey -> TaskScreenNecessaryComponents.TaskScreenTopAppBar()
                    is PowerCloudNavKey -> PowerScreenNecessaryComponents.PowerScreenTopAppBar()
                    else -> HomeScreenNecessaryComponents.HomeScreenTopAppBar()
                }
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

                    entry<TaskCloudNavKey> {
                        TaskScreen(innerPadding)
                    }

                    entry<PowerCloudNavKey> {
                        PowerScreen(innerPadding)
                    }

                    entry<SettingScreenNavKey> {
                        SettingScreen(innerPadding)
                    }
                }
            )

        }
    }
}

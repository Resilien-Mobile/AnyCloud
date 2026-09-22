package com.baidaidai.anycloud.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.baidaidai.anycloud.ui.component.settingScreen.SettingScreenNecessaryComponents
import com.baidaidai.anycloud.ui.component.taskScreen.TaskScreenNecessaryComponents
import com.baidaidai.anycloud.ui.viewmodel.NavigationViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StartScreenContainer() {

    // Navigation Area
    val navigation = rememberNavBackStack(HomeScreenNavKey)
    val currentDestination = navigation.last()

    // Coroutine Variable
    val coroutineScope = rememberCoroutineScope()

    // ViewModel Area
    val navigationViewModel = hiltViewModel<NavigationViewModel>()

    // NavigationDrawer Area
    val navigationDrawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val onNavigationButtonClick: () -> Unit = {
        coroutineScope.launch {
            navigationDrawerState.open()
        }
    }

    NavigationDrawer(
        navigationViewModel = navigationViewModel,
        navigationDrawerState = navigationDrawerState,
        onNavigationClick = { navigationConfig ->
            navigation.removeLastOrNull()
            navigation.add(navigationConfig.destinationNavKey)
        }
    ){
        Scaffold(
            topBar = {
                when(currentDestination){
                    is HomeScreenNavKey -> HomeScreenNecessaryComponents.HomeScreenTopAppBar(
                        onNavigationButtonClick = onNavigationButtonClick
                    )
                    is TaskCloudNavKey -> TaskScreenNecessaryComponents.TaskScreenTopAppBar(
                        onNavigationButtonClick = onNavigationButtonClick
                    )
                    is PowerCloudNavKey -> PowerScreenNecessaryComponents.PowerScreenTopAppBar(
                        onNavigationButtonClick = onNavigationButtonClick
                    )
                    is SettingScreenNavKey -> SettingScreenNecessaryComponents.SettingScreenTopAppBar(
                        onNavigationButtonClick = onNavigationButtonClick
                    )
                    else -> HomeScreenNecessaryComponents.HomeScreenTopAppBar(
                        onNavigationButtonClick = onNavigationButtonClick
                    )
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

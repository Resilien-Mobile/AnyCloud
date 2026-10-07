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
import com.baidaidai.anycloud.ui.navigation.things.ClipBoardPilotNavKey
import com.baidaidai.anycloud.ui.navigation.things.HomeScreenNavKey
import com.baidaidai.anycloud.ui.navigation.things.PowerCloudNavKey
import com.baidaidai.anycloud.ui.navigation.things.SettingScreenNavKey
import com.baidaidai.anycloud.ui.navigation.things.TaskCloudNavKey
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.ClipBoardPilotScreenNecessaryComponents
import com.baidaidai.anycloud.ui.component.things.homeScreen.HomeScreenNecessaryComponents
import com.baidaidai.anycloud.ui.component.navigation.NavigationDrawer
import com.baidaidai.anycloud.ui.component.things.powerScreen.PowerScreenNecessaryComponents
import com.baidaidai.anycloud.ui.component.things.settingScreen.SettingScreenNecessaryComponents
import com.baidaidai.anycloud.ui.component.things.taskScreen.TaskScreenNecessaryComponents
import com.baidaidai.anycloud.ui.screen.intelligent.ClipBoardPilotScreen
import com.baidaidai.anycloud.ui.screen.things.HomeScreen
import com.baidaidai.anycloud.ui.screen.things.PowerScreen
import com.baidaidai.anycloud.ui.screen.things.SettingScreen
import com.baidaidai.anycloud.ui.screen.things.TaskScreen
import com.baidaidai.anycloud.ui.viewmodel.navigation.NavigationViewModel
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
                    is ClipBoardPilotNavKey -> ClipBoardPilotScreenNecessaryComponents.ClipBoardPilotScreenTopAppBar(
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

                    entry<ClipBoardPilotNavKey> {
                        ClipBoardPilotScreen(innerPadding)
                    }
                }
            )

        }
    }
}

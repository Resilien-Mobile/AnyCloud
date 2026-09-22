package com.baidaidai.anycloud.ui.component.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.domain.navigation.HomeScreenNavKey
import com.baidaidai.anycloud.domain.navigation.NavigationConfig
import com.baidaidai.anycloud.domain.navigation.PowerCloudNavKey
import com.baidaidai.anycloud.domain.navigation.SettingScreenNavKey
import com.baidaidai.anycloud.domain.navigation.TaskCloudNavKey
import com.baidaidai.anycloud.ui.viewmodel.NavigationViewModel

@Composable
fun NavigationDrawer(
    navigationViewModel: NavigationViewModel,
    navigationDrawerState: DrawerState,
    onNavigationClick: (navigationConfig: NavigationConfig)-> Unit = {},
    content:  @Composable (() -> Unit)
){

    val dailyTrackScoreList by navigationViewModel.dailyTrackScoreList.collectAsState()
    val totalDayCount = navigationViewModel.totalDayCount.collectAsState().value.toString()
    val totalPlanCount = navigationViewModel.totalPlanCount.collectAsState().value.toString()

    val thingsNavigationList = listOf(
        NavigationConfig(
            destinationName = "Any Cloud",
            destinationIcon = R.drawable.material_symbols_cloud,
            destinationNavKey = HomeScreenNavKey
        ),
        NavigationConfig(
            destinationName = "Task Cloud",
            destinationIcon = R.drawable.material_symbols_task,
            destinationNavKey = TaskCloudNavKey
        ),
        NavigationConfig(
            destinationName = "Power Cloud",
            destinationIcon = R.drawable.material_symbols_bolt_boost,
            destinationNavKey = PowerCloudNavKey
        ),
        NavigationConfig(
            destinationName = "Setting",
            destinationIcon = R.drawable.material_symbols_settings,
            destinationNavKey = SettingScreenNavKey
        ),
    )
    val intelligentNavigationList = listOf(
        NavigationConfig(
            destinationName = "ClipBoard Pilot",
            destinationIcon = R.drawable.material_symbols_assistant_navigation,
            destinationNavKey = HomeScreenNavKey
        ),
    )
    var selectedDestination by remember { mutableStateOf(thingsNavigationList[0]) }

    ModalNavigationDrawer(
        drawerState = navigationDrawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerState = navigationDrawerState
            ) {

                // 软件标题
                Text(
                    text = "AnyCloud",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                )

                // 显示 “努力值” 积累榜单
                DailyTrackBoard(
                    dailyEffortList = dailyTrackScoreList,
                    modifier = Modifier.fillMaxWidth()
                )

                // Behind DailyTrackBoard
                // 显示运行 n 天，n 次规划
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = totalDayCount,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("天")
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = totalPlanCount,
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("次规划")
                    }
                }

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp))

                // Navigation Area
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = 8.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    // Things Navigation Area
                    item {
                        Text(
                            text = "Things",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(6.dp)
                                .padding(horizontal = 12.dp)
                        )
                    }

                    item {
                        NavigationDrawerItemList(
                            selectedDestination = selectedDestination,
                            navigationList = thingsNavigationList,
                        ){ navigationConfig ->
                            selectedDestination = navigationConfig
                            onNavigationClick(navigationConfig)
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Intelligent Navigation Area
                    item {
                        Text(
                            text = "Intelligent",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(6.dp)
                                .padding(horizontal = 12.dp)
                        )
                    }

                    item {
                        NavigationDrawerItemList(
                            selectedDestination = selectedDestination,
                            navigationList = intelligentNavigationList,
                        ){ navigationConfig ->
                            selectedDestination = navigationConfig
                            onNavigationClick(navigationConfig)
                        }
                    }

                }

            }
        }
    ) {
        content()
    }
}
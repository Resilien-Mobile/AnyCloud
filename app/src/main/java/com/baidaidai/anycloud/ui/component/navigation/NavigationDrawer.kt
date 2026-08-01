package com.baidaidai.anycloud.ui.component.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.domain.navigation.HomeScreenNavKey
import com.baidaidai.anycloud.domain.navigation.NavigationConfig
import com.baidaidai.anycloud.domain.navigation.PowerCloudNavKey
import com.baidaidai.anycloud.domain.navigation.TaskCloudNavKey

@Composable
fun NavigationDrawer(
    onNavigationClick: (navigationConfig: NavigationConfig)-> Unit = {},
    content:  @Composable (() -> Unit)
){

    val navigationList = listOf(
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
            destinationIcon = R.drawable.material_symbols_cable,
            destinationNavKey = PowerCloudNavKey
        ),
        NavigationConfig(
            destinationName = "Setting",
            destinationIcon = R.drawable.material_symbols_settings,
            destinationNavKey = PowerCloudNavKey
        ),
    )
    var selectedDestination by remember { mutableStateOf(navigationList[0]) }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerState = drawerState
            ) {

                ListItem(
                    leadingContent = {
                        Icon(
                            painter = painterResource(R.drawable.material_symbols_cloud),
                            contentDescription = null
                        )
                    },
                    headlineContent = {
                        Text("Any Cloud")
                    },
                    supportingContent = {
                        Text("v0.0.1")
                    }
                )

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp))
                Spacer(Modifier.height(8.dp))

                NavigationDrawerItemList(
                    selectedDestination = selectedDestination,
                    navigationList = navigationList,
                ){ navigationConfig ->
                    selectedDestination = navigationConfig
                    onNavigationClick(navigationConfig)
                }
            }
        }
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    NavigationDrawer() {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
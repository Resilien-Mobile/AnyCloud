package com.baidaidai.anycloud.ui.component.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.domain.navigation.HomeScreenNavKey
import com.baidaidai.anycloud.domain.navigation.NavigationConfig
import com.baidaidai.anycloud.domain.navigation.PowerCloudNavKey

@Composable
fun NavigationDrawerItemList(
    modifier: Modifier = Modifier,
    selectedDestination: NavigationConfig,
    navigationList: List<NavigationConfig>,
    onClick: (navigationConfig: NavigationConfig)->Unit = {}
){
    navigationList.forEachIndexed { index, navigationConfig ->
        NavigationDrawerItem(
            selected = selectedDestination.destinationName == navigationConfig.destinationName,
            onClick = {onClick(navigationConfig)},
            icon = {
                Icon(
                    painter = painterResource(navigationConfig.destinationIcon),
                    contentDescription = navigationConfig.destinationName
                )
            },
            label = {
                Text(navigationConfig.destinationName)
            },
            modifier = modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        if (index != navigationList.size-1){
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    val navigationList = listOf(
        NavigationConfig(
            destinationName = "Any Cloud",
            destinationIcon = R.drawable.material_symbols_cloud,
            destinationNavKey = HomeScreenNavKey
        ),
        NavigationConfig(
            destinationName = "Power",
            destinationIcon = R.drawable.material_symbols_cable,
            destinationNavKey = PowerCloudNavKey
        ),
        NavigationConfig(
            destinationName = "Upload",
            destinationIcon = R.drawable.material_symbols_arrow_upward,
            destinationNavKey = HomeScreenNavKey
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavigationDrawerItemList(
            selectedDestination = navigationList.first(),
            navigationList = navigationList
        ) { }
    }
}
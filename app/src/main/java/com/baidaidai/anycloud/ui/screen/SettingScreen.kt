package com.baidaidai.anycloud.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.ui.theme.getListItemColors
import com.baidaidai.anycloud.ui.vm.SettingScreenViewModel

@Composable
fun SettingScreen(
    innerPadding: PaddingValues,
    modifier: Modifier = Modifier,
    settingScreenViewModel: SettingScreenViewModel = hiltViewModel()
){
    val isOngoingStyleEnabled by settingScreenViewModel
        .isOngoingStyleEnabled
        .collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(innerPadding).padding(horizontal = 16.dp)) {

        // Cloud Display Setting
        ListItem(
            leadingContent = {
                Icon(
                    painter = painterResource(R.drawable.material_symbols_compare_arrows),
                    contentDescription = null
                )
            },
            trailingContent = {
                Switch(
                    checked = isOngoingStyleEnabled,
                    onCheckedChange = { isEnabled ->
                        settingScreenViewModel.syncOngoingStyleEnabled(
                            isEnabled = isEnabled
                        )
                    }
                )
            },
            headlineContent = {
                Text("Switch Ongoing Style")
            },
            supportingContent = {
                Text("Avoid having the OEM display the App Name, but instead show the remaining task quantity")
            },
            colors = getListItemColors(),
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        )

        // Behavior Setting


        // Artificial Intelligent Setting


        // Permission Settings ( Shizuku & Notification )

    }
}
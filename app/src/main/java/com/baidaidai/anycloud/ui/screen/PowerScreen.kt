package com.baidaidai.anycloud.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.ui.component.powerScreen.EnablePowerCloudRow
import com.baidaidai.anycloud.ui.component.powerScreen.EnergyGlanceRow
import com.baidaidai.anycloud.ui.vm.PowerScreenViewModel

@Composable
fun PowerScreen(
    innerPadding: PaddingValues,
    powerScreenViewModel: PowerScreenViewModel = hiltViewModel()
){

    val currentWatt by powerScreenViewModel.currentWatt.collectAsState()
    val currentAdapterType by powerScreenViewModel.currentAdapterType.collectAsState()
    val currentBatteryPercentage by powerScreenViewModel.currentBatteryPercentage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp),
    ){
        EnergyGlanceRow(
            firstTileHeadlineContent = "Adapter Type",
            firstTileContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentAdapterType.name,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            },
            secondTileHeadlineContent = "Current Watt",
            secondTileContent = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = String.format("%.2f", currentWatt),
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            },
            batteryPercentage = currentBatteryPercentage
        )

        Spacer(modifier = Modifier.height(10.dp))

        EnablePowerCloudRow {
            powerScreenViewModel.enablePowerCloud()
        }
    }
}

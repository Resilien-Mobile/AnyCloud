package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baidaidai.anycloud.domain.shizuku.ShizukuStatus
import com.baidaidai.anycloud.ui.R

@Composable
fun ShizukuStatusDashBoard(
    modifier: Modifier = Modifier,
    shizukuStatus: ShizukuStatus,
    onClick: ()-> Unit = {}
){

    val imageResource = when(shizukuStatus){
        ShizukuStatus.UNAUTHORIZED -> R.drawable.material_symbols_error
        ShizukuStatus.UNAVAILABLE -> R.drawable.material_symbols_globe_2_cancel
        ShizukuStatus.AUTHORIZED -> R.drawable.material_symbols_check_circle
    }

    val backgroundColor = when(shizukuStatus){
        ShizukuStatus.UNAUTHORIZED -> MaterialTheme.colorScheme.error
        ShizukuStatus.UNAVAILABLE -> MaterialTheme.colorScheme.error
        ShizukuStatus.AUTHORIZED -> MaterialTheme.colorScheme.primaryContainer
    }

    val title = when(shizukuStatus){
        ShizukuStatus.UNAUTHORIZED -> "Shizuku is not authorized"
        ShizukuStatus.UNAVAILABLE -> "Shizuku is unavailable"
        ShizukuStatus.AUTHORIZED -> "Shizuku is connected"
    }

    val supporting = when(shizukuStatus){
        ShizukuStatus.UNAUTHORIZED -> "Please check the Shizuku authorization status."
        ShizukuStatus.UNAVAILABLE -> "Please check whether Shizuku is installed and running."
        ShizukuStatus.AUTHORIZED -> "Shizuku status is working normally."
    }

    val titleColor = when(shizukuStatus){
        ShizukuStatus.UNAUTHORIZED -> MaterialTheme.colorScheme.onError
        ShizukuStatus.UNAVAILABLE -> MaterialTheme.colorScheme.onError
        ShizukuStatus.AUTHORIZED -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    val supportingColor = when(shizukuStatus){
        ShizukuStatus.UNAUTHORIZED -> MaterialTheme.colorScheme.onError
        ShizukuStatus.UNAVAILABLE -> MaterialTheme.colorScheme.onError
        ShizukuStatus.AUTHORIZED -> MaterialTheme.colorScheme.onPrimaryContainer
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(backgroundColor)
            .fillMaxWidth()
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .padding(20.dp)
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = null,
            colorFilter = ColorFilter.tint(titleColor),
            modifier = Modifier
                .size(48.dp)
        )

        Spacer(modifier = Modifier.width(20.dp))
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.W600,
                color = titleColor
            )

            Text(
                text = supporting,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.W400,
                color = supportingColor
            )
        }

    }
}

@PreviewLightDark
@Composable
private fun _preview_() {

    val shizukuStatus = ShizukuStatus.UNAVAILABLE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ShizukuStatusDashBoard(shizukuStatus = shizukuStatus)
    }

}

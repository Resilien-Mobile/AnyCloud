package com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumFlexibleTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.theme.getPrimaryIconButtonColors
import com.baidaidai.anycloud.ui.theme.getTopAppBarIconButtonColors

object PolicyManagerScreenNecessaryComponents {

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun PolicyManagerScreenTopAppBar(
        onSwitchClick: ()-> Unit = {},
        onNavigationButtonClick: () -> Unit = {}
    ) {
        MediumFlexibleTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onNavigationButtonClick,
                    colors = getTopAppBarIconButtonColors()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.material_symbols_menu),
                        contentDescription = "Menu",
                    )
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(intrinsicSize = IntrinsicSize.Min)
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Policy Manager",
                        style = MaterialTheme.typography.displaySmall
                    )
                    IconButton(
                        colors = getPrimaryIconButtonColors(),
                        onClick = onSwitchClick,
                        shape = CircleShape,
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.material_symbols_sync_alt),
                            contentDescription = "Change to Policy group"
                        )
                    }
                }
            },
            subtitle = {
                Text(
                    text = ""
                )
            }
        )
    }
}

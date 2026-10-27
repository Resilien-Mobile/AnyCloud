package com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ButtonGroupDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.theme.getReadyPrimaryButtonColors
import com.baidaidai.anycloud.ui.theme.getSelectedPrimaryButtonColors

@Composable
fun ImportMethodButtonGroup(
    modifier: Modifier = Modifier,
    currentSelectedIndex: Int = 0,
    onSelectedChange: (index: Int) -> Unit = {},
    onInputClick: () -> Unit = {},
    onScanClick: () -> Unit = {},
    onUriClick: () -> Unit = {},
){



    ButtonGroup(
        overflowIndicator = { menuState ->
            ButtonGroupDefaults.OverflowIndicator(
                menuState = menuState
            )
        },
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .height(32.dp)
            .fillMaxWidth()
            .clip(
                shape = CircleShape
            )
    ) {


        customItem(
            buttonGroupContent = {
                Button(
                    onClick = {
                        onSelectedChange(0)
                        onInputClick()
                    },
                    shape = RoundedCornerShape(
                        topEnd = 8.dp,
                        bottomEnd = 8.dp
                    ),
                    colors = resolveButtonColor(0,currentSelectedIndex),
                    modifier = Modifier
                        .height(32.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Input"
                    )
                }
            },
            menuContent = {}
        )

        customItem(
            buttonGroupContent = {
                Button(
                    onClick = {
                        onSelectedChange(1)
                        onScanClick()
                    },
                    colors = resolveButtonColor(1,currentSelectedIndex),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(32.dp)
                        .weight(1f)
                ) {
                    Text("Scan")
                }
            },
            menuContent = {}
        )

        customItem(
            buttonGroupContent = {
                Button(
                    onClick = {
                        onSelectedChange(2)
                        onUriClick()
                    },
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        bottomStart = 8.dp
                    ),
                    colors = resolveButtonColor(2,currentSelectedIndex),
                    modifier = Modifier
                        .height(32.dp)
                        .weight(1f)
                ) {
                    Text("URI")
                }
            },
            menuContent = {}
        )

    }

}

@Composable
private fun resolveButtonColor(
    buttonIndex: Int,
    selectedIndex: Int
): ButtonColors {
    return if (buttonIndex == selectedIndex) {
        getSelectedPrimaryButtonColors()
    } else {
        getReadyPrimaryButtonColors()
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {

    var currentSelectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer
            )
            .padding(16.dp)
    ) {
        ImportMethodButtonGroup(
            currentSelectedIndex = currentSelectedIndex,
            onSelectedChange = {
                currentSelectedIndex = it
            },
            modifier = Modifier

        )

    }
}

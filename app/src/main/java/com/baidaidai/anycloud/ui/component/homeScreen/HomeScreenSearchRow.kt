package com.baidaidai.anycloud.ui.component.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R
import com.baidaidai.anycloud.ui.theme.getIconButtonColors
import com.baidaidai.anycloud.ui.theme.getTextFieldColors

@Composable
fun HomeScreenSearchRow(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    onSearchRowSizeChange: (maxHeight: Dp, maxWidth: Dp) -> Unit = { _, _ ->},
    onSendButtonClick: ()->Unit = {},
){

    val density = LocalDensity.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .background(Color.Transparent)
            .onSizeChanged { size ->
                val heightDp = with(density) { size.height.toDp() }
                val widthDp = with(density) { size.width.toDp() }
                onSearchRowSizeChange(heightDp, widthDp)
            }
    ) {

        OutlinedTextField(
            state = state,
            placeholder = {
                Text(
                    text = "what to do today?",
                    color = MaterialTheme.colorScheme.primary,
                )
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            shape = CircleShape,
            contentPadding = PaddingValues(horizontal = 24.dp),
            colors = getTextFieldColors(),
            modifier = Modifier
                .weight(1f)
        )

        IconButton(
            shape = CircleShape,
            onClick = onSendButtonClick,
            colors = getIconButtonColors(),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
        ) {
            Icon(
                painter = painterResource(R.drawable.material_symbols_arrow_upward),
                contentDescription = "",
            )
        }

    }

}

@PreviewLightDark
@Composable
private fun _preview_() {
    val inputContentState = rememberTextFieldState()

    HomeScreenSearchRow(
        state = inputContentState,
        modifier = Modifier
            .width(600.dp)
            .background(
                color = MaterialTheme.colorScheme.surface
            )
            .padding(16.dp)
    )
}
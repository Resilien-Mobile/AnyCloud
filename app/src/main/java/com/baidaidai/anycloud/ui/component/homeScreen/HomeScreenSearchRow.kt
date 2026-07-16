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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R

@Composable
fun HomeScreenSearchRow(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    onSendButtonClick: ()->Unit = {},
){

    val iconButtonColors = IconButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onPrimary
    )

    val textFieldContainerColor = MaterialTheme.colorScheme.surface
    val textFieldContentColor = MaterialTheme.colorScheme.primary
    val textFieldColors = TextFieldColors(
        focusedTextColor = textFieldContentColor,
        unfocusedTextColor = textFieldContentColor,
        disabledTextColor = textFieldContentColor,
        errorTextColor = textFieldContentColor,
        focusedContainerColor = textFieldContainerColor,
        unfocusedContainerColor = textFieldContainerColor,
        disabledContainerColor = textFieldContainerColor,
        errorContainerColor = textFieldContainerColor,
        cursorColor = textFieldContentColor,
        errorCursorColor = textFieldContentColor,
        textSelectionColors = TextSelectionColors(
            handleColor = textFieldContentColor,
            backgroundColor = textFieldContentColor
        ),
        focusedIndicatorColor = textFieldContentColor,
        unfocusedIndicatorColor = textFieldContentColor,
        disabledIndicatorColor = textFieldContentColor,
        errorIndicatorColor = textFieldContentColor,
        focusedLeadingIconColor = textFieldContentColor,
        unfocusedLeadingIconColor = textFieldContentColor,
        disabledLeadingIconColor = textFieldContentColor,
        errorLeadingIconColor = textFieldContentColor,
        focusedTrailingIconColor = textFieldContentColor,
        unfocusedTrailingIconColor = textFieldContentColor,
        disabledTrailingIconColor = textFieldContentColor,
        errorTrailingIconColor = textFieldContentColor,
        focusedLabelColor = textFieldContentColor,
        unfocusedLabelColor = textFieldContentColor,
        disabledLabelColor = textFieldContentColor,
        errorLabelColor = textFieldContentColor,
        focusedPlaceholderColor = textFieldContentColor,
        unfocusedPlaceholderColor = textFieldContentColor,
        disabledPlaceholderColor = textFieldContentColor,
        errorPlaceholderColor = textFieldContentColor,
        focusedSupportingTextColor = textFieldContentColor,
        unfocusedSupportingTextColor = textFieldContentColor,
        disabledSupportingTextColor = textFieldContentColor,
        errorSupportingTextColor = textFieldContentColor,
        focusedPrefixColor = textFieldContentColor,
        unfocusedPrefixColor = textFieldContentColor,
        disabledPrefixColor = textFieldContentColor,
        errorPrefixColor = textFieldContentColor,
        focusedSuffixColor = textFieldContentColor,
        unfocusedSuffixColor = textFieldContentColor,
        disabledSuffixColor = textFieldContentColor,
        errorSuffixColor = textFieldContentColor
    )

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
            colors = textFieldColors,
            modifier = Modifier
                .weight(1f)
        )
        IconButton(
            shape = CircleShape,
            onClick = onSendButtonClick,
            colors = iconButtonColors,
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
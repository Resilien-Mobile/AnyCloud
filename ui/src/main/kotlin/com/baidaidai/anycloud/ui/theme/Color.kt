package com.baidaidai.anycloud.ui.theme

import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


@Composable
fun getTextFieldColors(): TextFieldColors {
    return TextFieldColors(
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        disabledTextColor = MaterialTheme.colorScheme.primary,
        errorTextColor = MaterialTheme.colorScheme.primary,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        cursorColor = MaterialTheme.colorScheme.primary,
        errorCursorColor = MaterialTheme.colorScheme.primary,
        textSelectionColors = TextSelectionColors(
            handleColor = MaterialTheme.colorScheme.primary,
            backgroundColor = MaterialTheme.colorScheme.primary
        ),
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
        disabledIndicatorColor = MaterialTheme.colorScheme.primary,
        errorIndicatorColor = MaterialTheme.colorScheme.primary,
        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        disabledLeadingIconColor = MaterialTheme.colorScheme.primary,
        errorLeadingIconColor = MaterialTheme.colorScheme.primary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        disabledTrailingIconColor = MaterialTheme.colorScheme.primary,
        errorTrailingIconColor = MaterialTheme.colorScheme.primary,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
        disabledLabelColor = MaterialTheme.colorScheme.primary,
        errorLabelColor = MaterialTheme.colorScheme.primary,
        focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
        disabledPlaceholderColor = MaterialTheme.colorScheme.primary,
        errorPlaceholderColor = MaterialTheme.colorScheme.primary,
        focusedSupportingTextColor = MaterialTheme.colorScheme.primary,
        unfocusedSupportingTextColor = MaterialTheme.colorScheme.primary,
        disabledSupportingTextColor = MaterialTheme.colorScheme.primary,
        errorSupportingTextColor = MaterialTheme.colorScheme.primary,
        focusedPrefixColor = MaterialTheme.colorScheme.primary,
        unfocusedPrefixColor = MaterialTheme.colorScheme.primary,
        disabledPrefixColor = MaterialTheme.colorScheme.primary,
        errorPrefixColor = MaterialTheme.colorScheme.primary,
        focusedSuffixColor = MaterialTheme.colorScheme.primary,
        unfocusedSuffixColor = MaterialTheme.colorScheme.primary,
        disabledSuffixColor = MaterialTheme.colorScheme.primary,
        errorSuffixColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun getIconButtonColors(): IconButtonColors  {
    return IconButtonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary,
        disabledContentColor = MaterialTheme.colorScheme.onPrimary
    )
}

@Composable
fun getListItemColors(): ListItemColors {

    return ListItemColors(
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        leadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        trailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        overlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        supportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledLeadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledTrailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledOverlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledSupportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedLeadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedTrailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedOverlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        selectedSupportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        draggedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        draggedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        draggedLeadingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        draggedTrailingContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        draggedOverlineContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        draggedSupportingContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    )

}

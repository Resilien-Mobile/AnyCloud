package com.baidaidai.anycloud.ui.layout.intelligent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.component.intelligent.policyManagerScreen.ImportMethodButtonGroup

@Composable
fun PolicyBottomSheetLayout(
    modifier: Modifier = Modifier,
    currentSelectedIndex: Int = 0,
    onSelectedChange: (index: Int) -> Unit = {},
    contentPaddingValues: PaddingValues,
    content: @Composable (()-> Unit),
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = contentPaddingValues.calculateBottomPadding()
            )
    ) {

        ImportMethodButtonGroup(
            currentSelectedIndex = currentSelectedIndex,
            onSelectedChange = onSelectedChange
        )

        content()

    }

}

@PreviewLightDark
@Composable
private fun _preview_() {

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        PolicyBottomSheetLayout(
            contentPaddingValues = PaddingValues(bottom = 20.dp),
            content = {}
        )
    }

}
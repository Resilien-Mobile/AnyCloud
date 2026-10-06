package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.collection.intListOf
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuGroup
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.DropdownMenuPopup
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorPosition
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.R

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun <T> SelectableTextField(
    modifier: Modifier = Modifier,
    label: String,
    selectedOption: T,
    optionList: List<T>,
    optionText: (T) -> String,
    onOptionSelected: (T) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    val popupPositionProvider = MenuDefaults.rememberDropdownMenuPopupPositionProvider(

        // 根据父元素计算信息，计算自定义展开原点
        dropdownMenuAnchorPosition = MenuAnchorPosition.Custom(

            xCandidates = {
                intListOf(
                    // composition规则列表
                    anchorBounds.right - menuSize.width
                )
            },

            yCandidates = {
                intListOf(
                    // composition规则列表
                    anchorBounds.bottom,
                )
            }

        ),

        // 基于上方计算结果，再次进行偏移
        offset = DpOffset(
            x = 0.dp,
            y = 4.dp
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = optionText(selectedOption),
            onValueChange = {},
            readOnly = true,
            label = {
                Text(label)
            },
            trailingIcon = {
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.material_symbols_arrow_drop_down),
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        DropdownMenuPopup(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            popupPositionProvider = popupPositionProvider
        ) {

            DropdownMenuGroup(
                shapes = MenuDefaults.groupShapes()
            ) {
                optionList.forEachIndexed { index, option ->
                    DropdownMenuItem(
                        checked = selectedOption == option,
                        onCheckedChange = {
                            if (selectedOption != option) {
                                onOptionSelected(option)
                            }
                            isExpanded = false
                        },
                        text = {
                            Text(optionText(option))
                        },
                        shapes = MenuDefaults.itemShape(
                            index = index,
                            count = optionList.size
                        ),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    var selectedOption by remember { mutableStateOf("DOMAIN") }

    Column {
        SelectableTextField(
            label = "分流规则",
            selectedOption = selectedOption,
            optionList = listOf("DOMAIN", "DOMAIN_SUFFIX", "IP_CIDR"),
            optionText = { option -> option },
            onOptionSelected = { option ->
                selectedOption = option
            }
        )
    }
}

package com.baidaidai.anycloud.ui.screen.intelligent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.domain.clipboard.ClipboardPolicy
import com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen.PolicyBottomSheetContent

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PolicyManagerScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    clipboardPolicyList: List<ClipboardPolicy>
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = contentPadding.calculateTopPadding()
            )
            .padding(top = 16.dp)

    ) {

//        if (clipboardPolicyList.isEmpty()){
//            TODO("Empty Placeholder")
//        }else{
//            clipboardPolicyList.forEach { clipboardPolicy ->
//                TODO("ListItem List $clipboardPolicy")
//            }
//        }

    }

}
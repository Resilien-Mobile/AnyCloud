package com.baidaidai.anycloud.ui.screen.intelligent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.component.common.VerticalSpacer
import com.baidaidai.anycloud.ui.theme.getExpressiveListItemShape
import com.baidaidai.anycloud.ui.theme.getListItemColors
import com.baidaidai.anycloud.ui.viewmodel.intelligent.PolicyGroupScreenViewModel

@Composable
fun PolicyGroupScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues,
    policyGroupScreenViewModel: PolicyGroupScreenViewModel = hiltViewModel()
){
    // Values
    val clipboardGroupList by policyGroupScreenViewModel.clipboardGroupList.collectAsState()

    if (clipboardGroupList.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxSize()
        ) {
            Icon(
                painter = painterResource(R.drawable.material_symbols_folder_open),
                modifier = Modifier.size(64.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f)
            )
            Text(
                text = "No Groups",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f)
            )
        }
    }else{
        LazyColumn(
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = contentPadding.calculateBottomPadding()
            ),
            modifier = modifier
                .fillMaxSize()
                .padding(
                    top = contentPadding.calculateTopPadding()
                )

        ) {
            clipboardGroupList.forEachIndexed { index, clipboardGroup ->
                item{
                    ListItem(
                        headlineContent = {
                            Text(
                                text = clipboardGroup.groupName
                            )
                        },
                        supportingContent = {
                            Text(
                                text = clipboardGroup.targetPackageName
                            )
                        },
                        colors = getListItemColors(),
                        modifier = Modifier
                            .clip(
                                shape = getExpressiveListItemShape(
                                    index = index,
                                    list = clipboardGroupList
                                )
                            )
                    )
                }

                if (index != clipboardGroupList.lastIndex){
                    item{
                        VerticalSpacer(height = 2.dp)
                    }
                }
            }
        }
    }
}

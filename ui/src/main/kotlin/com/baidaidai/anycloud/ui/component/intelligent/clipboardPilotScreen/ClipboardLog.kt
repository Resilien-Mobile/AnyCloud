package com.baidaidai.anycloud.ui.component.intelligent.clipboardPilotScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.theme.getListItemColors

@Composable
fun ClipboardLog(
    modifier: Modifier = Modifier,
){

    val fakeList = MutableList(20){}

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(12.dp)
            )
    ) {
        itemsIndexed(
            items = fakeList
        ){ index, content ->
            ListItem(
                colors = getListItemColors(),
                leadingContent = {
                    Image(
                        painter = painterResource(com.baidaidai.anycloud.ui.R.drawable.xhs),
                        contentDescription = "",
                        modifier = Modifier.clip(CircleShape)
                    )
                },
                headlineContent = {
                    Text("http://just.a.test.org")
                },
                supportingContent = {
                    Text("Matched xhs")
                }
            )
            if (index != fakeList.size-1){
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun _preview_() {
    ClipboardLog(
    )
}
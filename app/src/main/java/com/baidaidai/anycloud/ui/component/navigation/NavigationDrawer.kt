package com.baidaidai.anycloud.ui.component.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    content:  @Composable (() -> Unit)
){
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {

                ListItem(
                    leadingContent = {
                        Icon(
                            painter = painterResource(R.drawable.material_symbols_cloud),
                            contentDescription = null
                        )
                    },
                    headlineContent = {
                        Text("Any Cloud")
                    },
                    supportingContent = {
                        Text("v0.0.1")
                    }
                )

                Spacer(Modifier.height(8.dp))
                HorizontalDivider(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp))
                Spacer(Modifier.height(8.dp))

                NavigationDrawerItem(
                    selected = true,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(
                                R.drawable.material_symbols_cloud
                            ),
                            contentDescription = null
                        )
                    },
                    label = {
                        Text("Any Cloud")
                    },
                    modifier = Modifier.padding(
                        NavigationDrawerItemDefaults.ItemPadding
                    )
                )
            }
        }
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    NavigationDrawer {
        Scaffold { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

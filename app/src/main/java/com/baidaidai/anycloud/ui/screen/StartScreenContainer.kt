package com.baidaidai.anycloud.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StartScreenContainer() {
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
                HorizontalDivider(modifier = Modifier.fillMaxWidth().height(1.dp))
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
        Scaffold(
            topBar = {
                LargeFlexibleTopAppBar(
                    title = {
                        Text(
                            text = "Any Cloud",
                            style = MaterialTheme.typography.displaySmall
                        )
                    },
                    subtitle = {
                        Text(
                            text = ""
                        )
                    }
                )
            }
        ) { innerPadding ->
            if (false){
                PowerScreen(innerPadding)
            }else{
                HomeScreen(innerPadding)
            }
        }
    }
}

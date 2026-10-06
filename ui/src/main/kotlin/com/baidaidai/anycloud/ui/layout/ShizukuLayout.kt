package com.baidaidai.anycloud.ui.layout

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.baidaidai.anycloud.ui.component.intelligent.shizukuAdbScreen.ShizukuAdbScreenNecessaryComponents
import com.baidaidai.anycloud.ui.screen.intelligent.ShizukuAdbScreen
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme
import com.baidaidai.anycloud.ui.viewmodel.intelligent.ShizukuAdbScreenError
import com.baidaidai.anycloud.ui.viewmodel.intelligent.ShizukuAdbScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShizukuLayout() {
    val shizukuAdbScreenViewModel = hiltViewModel<ShizukuAdbScreenViewModel>()
    var currentError by remember { mutableStateOf<ShizukuAdbScreenError?>(null) }

    LaunchedEffect(Unit) {
        shizukuAdbScreenViewModel.shizukuError.collect { error ->
            currentError = error
        }
    }

    AnyCloudTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Shizuku")
                    }
                )
            }
        ) { contentPadding ->

            // Error Dialog
            if (currentError is ShizukuAdbScreenError) {
                ShizukuAdbScreenNecessaryComponents.ShizukuAdbScreenErrorDialog(
                    shizukuAdbScreenViewModel = shizukuAdbScreenViewModel,
                    error = currentError
                )
            }

            // Screen Content
            ShizukuAdbScreen(
                contentPaddingValues = contentPadding,
                shizukuAdbScreenViewModel = shizukuAdbScreenViewModel
            )
        }
    }
}

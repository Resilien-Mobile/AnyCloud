package com.baidaidai.anycloud.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.baidaidai.anycloud.ui.layout.ShizukuLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShizukuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ShizukuLayout()
        }
    }
}

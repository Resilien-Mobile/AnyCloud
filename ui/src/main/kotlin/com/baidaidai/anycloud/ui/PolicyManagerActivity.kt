package com.baidaidai.anycloud.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.baidaidai.anycloud.ui.layout.PolicyManagerLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PolicyManagerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PolicyManagerLayout()
        }
    }
}

package com.baidaidai.anycloud.ui.component.intelligent.shizukuAdbScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.baidaidai.anycloud.ui.R
import com.baidaidai.anycloud.ui.theme.AnyCloudTheme
import com.baidaidai.anycloud.ui.viewmodel.intelligent.ShizukuAdbScreenError
import com.baidaidai.anycloud.ui.viewmodel.intelligent.ShizukuAdbScreenViewModel

object ShizukuAdbScreenNecessaryComponents {

    @Composable
    fun ShizukuAdbScreenErrorDialog(
        shizukuAdbScreenViewModel: ShizukuAdbScreenViewModel,
        error: ShizukuAdbScreenError?
    ) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                Button(
                    onClick = {
                        shizukuAdbScreenViewModel.dismissShizukuError()
                    }
                ) {
                    Text("Ok")
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.material_symbols_error),
                    contentDescription = "Dialog Warning Logo"
                )
            },
            title = {
                Text(error!!.errorMessage)
            },
            text = {
                Text(
                    text = error!!.errorCause,
                    modifier = Modifier
                        .heightIn(max = 300.dp)
                        .verticalScroll(
                            state = rememberScrollState()
                        )
                )
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ShizukuAdbScreenModalSheet(
        remainingSeconds: Int,
        onDismissRequest: () -> Unit,
        onDismissCompletion: () -> Unit,
        onReturnToApp: () -> Unit
    ) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = rememberModalBottomSheetState(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 8.dp, bottom = 24.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(84.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.material_symbols_check),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Shizuku is ready",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Returning to AnyCloud in $remainingSeconds seconds.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = onDismissCompletion,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Close")
                    }

                    Button(
                        onClick = onReturnToApp,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Return")
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun ShizukuAdbScreenActionCard(
        step: String,
        title: String,
        description: String,
        isTargetActive: Boolean,
        onClick: () -> Unit
    ) {
        Card(
            elevation = CardDefaults.cardElevation(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(20.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Column {
                        Text(
                            text = step,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMediumEmphasized
                        )
                    }

                    Button(
                        onClick = onClick,
                        modifier = Modifier.size(48.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Icon(
                            painter = if (isTargetActive) {
                                painterResource(R.drawable.material_symbols_check)
                            } else {
                                painterResource(R.drawable.material_symbols_play_arrow)
                            },
                            contentDescription = "Start",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMediumEmphasized
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    fun ShizukuAdbScreenOverviewCard() {
        OutlinedCard(
            elevation = CardDefaults.cardElevation(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Shizuku ADB",
                        style = MaterialTheme.typography.titleLargeEmphasized
                    )

                    Icon(
                        painter = painterResource(R.drawable.material_shizuku_icon),
                        contentDescription = "Shizuku",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }

                Text(
                    text = "Use Shizuku to prepare the clipboard pilot service.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun _preview_() {
    AnyCloudTheme(dynamicColor = false) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            ShizukuAdbScreenNecessaryComponents.ShizukuAdbScreenOverviewCard()
            ShizukuAdbScreenNecessaryComponents.ShizukuAdbScreenActionCard(
                step = "STEP 1",
                title = "Authorize Shizuku",
                description = "Grant AnyCloud permission to use Shizuku.",
                isTargetActive = false
            ) { }
        }
    }
}

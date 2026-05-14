package com.charan.norton.features.scan.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.charan.norton.common.components.PrimaryButton
import com.charan.norton.common.components.SubTitleText
import com.charan.norton.common.components.TitleText
import androidx.compose.ui.res.stringResource
import com.charan.norton.R
import com.charan.norton.common.theme.NortonTheme
import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.presentation.components.CheckItemRow
import com.charan.norton.features.scan.presentation.components.ScanProgressIndicator

@Composable
fun ScanScreen(
    viewModel: ScanViewModel = hiltViewModel(),
    onBack: () -> Unit = {},
    onViewResults: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(ScanAction.StartScan)
    }

    ScanContent(state = state, onBack = onBack, onViewResults = onViewResults)
}

@Composable
fun ScanContent(state: ScanState, onBack: () -> Unit, onViewResults: () -> Unit) {

    // Current scanning check label
    val scanCompleteLabel = stringResource(R.string.scan_complete)
    val scanCheckingFormat = stringResource(R.string.scan_checking_format)
    val currentCheckLabel = when {
        state.isComplete -> scanCompleteLabel
        else -> state.checks
            .getOrNull(state.currentCheckIndex)
            ?.let { scanCheckingFormat.format(it.title.lowercase()) }
            ?: ""
    }

    val completedCount = state.checks.count { it.status == CheckStatus.COMPLETE }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            TitleText(text = stringResource(R.string.scan_title))
        }

        SubTitleText(
            text = stringResource(R.string.scan_subtitle),
            modifier = Modifier.padding(top = 4.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 15.dp),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ScanProgressIndicator(progress = state.progress)

                // Current check label
                Text(
                    text = currentCheckLabel,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        if (state.checks.isNotEmpty()) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.scan_checks_header),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            letterSpacing = TextUnit(1.2f, TextUnitType.Sp)
                        )
                        Text(
                            text = stringResource(R.string.scan_checks_progress, completedCount, state.checks.size),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        thickness = 0.5.dp
                    )

                    state.checks.forEachIndexed { index, check ->
                        CheckItemRow(check = check)
                        if (index < state.checks.lastIndex) {
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                thickness = 0.5.dp
                            )
                        }
                    }
                }
            }
        }

        if (state.isComplete) {
            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = stringResource(R.string.scan_view_results),
                modifier = Modifier.fillMaxWidth(),
                onClick = onViewResults
            )
        }
    }
}

@Preview
@Composable
private fun ScanScreenPreviewDark() {
    NortonTheme(darkTheme = true, dynamicColor = false) {
        ScanContent(
            onBack = {},
            state = ScanState(
                checks = listOf(
                    ScanCheck(
                        "OS Version",
                        "Android 14 · up to date",
                        CheckStatus.SECURE
                    ),
                    ScanCheck(
                        "App Threats",
                        "Reviewing 142 installed apps...",
                        CheckStatus.SCANNING
                    ),
                    ScanCheck(
                        "Wi-Fi Safety",
                        "Will check after app scan completes",
                        CheckStatus.PENDING
                    ),
                    ScanCheck(
                        "Password Strength",
                        "Will check saved credentials",
                        CheckStatus.PENDING
                    )
                ),
                currentCheckIndex = 1,
                isScanning = true
            ),
            onViewResults = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanScreenPreviewLight() {
    NortonTheme(darkTheme = false, dynamicColor = false) {
        ScanContent(
            onBack = {},
            state = ScanState(
                checks = listOf(
                    ScanCheck(
                        "OS Version",
                        "Android 14 · up to date",
                        CheckStatus.SECURE
                    ),
                    ScanCheck(
                        "App Threats",
                        "Reviewing 142 installed apps...",
                        CheckStatus.SCANNING
                    ),
                    ScanCheck(
                        "Wi-Fi Safety",
                        "Will check after app scan completes",
                        CheckStatus.PENDING
                    ),
                    ScanCheck(
                        "Password Strength",
                        "Will check saved credentials",
                        CheckStatus.PENDING
                    )
                ),
                currentCheckIndex = 1,
                isScanning = true
            ),
            onViewResults = {}
        )
    }
}
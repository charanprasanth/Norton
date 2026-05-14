package com.charan.norton.features.scan.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.charan.norton.common.components.SubTitleText
import com.charan.norton.common.components.TitleText
import com.charan.norton.common.theme.DangerColorDark
import com.charan.norton.common.theme.NortonTheme
import com.charan.norton.common.theme.SafeColorDark
import com.charan.norton.common.theme.WarningColorDark
import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.presentation.components.CheckItemRow

@Composable
fun ScanResultScreen(
    viewModel: ScanResultViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    ScanResultContent(scanResult = viewModel.scanResult, onBack = onBack)
}

@Composable
fun ScanResultContent(scanResult: SecurityScore?, onBack: () -> Unit) {
    val checks = scanResult?.checks ?: emptyList()
    val score = scanResult?.overallScore ?: 0
    val needsReviewCount = checks.count { it.status == CheckStatus.WARNING }

    var targetScore by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        targetScore = score / 100f
    }

    val animatedScore by animateFloatAsState(
        targetValue = targetScore,
        animationSpec = tween(durationMillis = 1200),
        label = "score_animation"
    )

    val scoreColor = when {
        score >= 80 -> SafeColorDark
        score >= 50 -> WarningColorDark
        else -> DangerColorDark
    }

    val summaryTitle = when {
        score >= 80 -> "Device is secure"
        score >= 50 -> "Device is mostly secure"
        else -> "Device needs attention"
    }

    val summarySubtitle = when {
        needsReviewCount == 0 -> "All checks passed. Your device is protected."
        needsReviewCount == 1 -> "1 check needs your attention. Tap each item to fix it."
        else -> "$needsReviewCount checks need your attention. Tap each item to fix it."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            TitleText(text = "Scan results")
        }

        SubTitleText(
            text = "Last scan · just now",
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(160.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { 1f },
                        modifier = Modifier.size(160.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                        trackColor = androidx.compose.ui.graphics.Color.Transparent,
                        strokeWidth = 12.dp,
                        strokeCap = StrokeCap.Round
                    )

                    CircularProgressIndicator(
                        progress = { animatedScore },
                        modifier = Modifier.size(160.dp),
                        color = scoreColor,
                        trackColor = androidx.compose.ui.graphics.Color.Transparent,
                        strokeWidth = 12.dp,
                        strokeCap = StrokeCap.Round
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${(animatedScore * 100).toInt()}",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "SCORE",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            letterSpacing = TextUnit(1.5f, TextUnitType.Sp)
                        )
                        Text(
                            text = "out of 100",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = summaryTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = summarySubtitle,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

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
                        text = "ALL CHECKS",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        letterSpacing = TextUnit(1.2f, TextUnitType.Sp)
                    )
                    if (needsReviewCount > 0) {
                        Text(
                            text = "$needsReviewCount NEED REVIEW",
                            style = MaterialTheme.typography.labelSmall,
                            color = WarningColorDark,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = TextUnit(0.5f, TextUnitType.Sp)
                        )
                    }
                }

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    thickness = 0.5.dp
                )

                checks.forEachIndexed { index, check ->
                    CheckItemRow(check = check)
                    if (index < checks.lastIndex) {
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
}

private val previewScore = SecurityScore(
    overallScore = 50,
    checks = listOf(
        ScanCheck("OS Version", "Android 14 · up to date", CheckStatus.SECURE),
        ScanCheck("App Threats", "142 apps reviewed · none flagged", CheckStatus.SECURE),
        ScanCheck("Wi-Fi Safety", "Open network · consider VPN", CheckStatus.WARNING),
        ScanCheck("Password Strength", "2 reused passwords found", CheckStatus.WARNING),
    )
)

@Preview
@Composable
private fun ScanResultScreenPreviewDark() {
    NortonTheme(darkTheme = true, dynamicColor = false) {
        ScanResultContent(scanResult = previewScore, onBack = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun ScanResultScreenPreviewLight() {
    NortonTheme(darkTheme = false, dynamicColor = false) {
        ScanResultContent(scanResult = previewScore, onBack = {})
    }
}
package com.charan.norton.features.genie.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.charan.norton.common.components.PrimaryButton
import com.charan.norton.common.theme.DangerColorDark
import com.charan.norton.common.theme.NortonTheme
import com.charan.norton.common.theme.SafeColorDark
import com.charan.norton.common.theme.WarningColorDark
import com.charan.norton.features.genie.domain.model.RiskLevel

// Mapping RiskLevel to UI properties
private data class RiskUi(
    val label: String,
    val icon: ImageVector,
    val color: Color,
    val containerColor: Color,
    val headline: String
)

private fun RiskLevel.toUi(): RiskUi = when (this) {
    RiskLevel.SAFE -> RiskUi(
        label = "Safe",
        icon = Icons.Filled.CheckCircle,
        color = SafeColorDark,
        containerColor = SafeColorDark.copy(alpha = 0.2f),
        headline = "Looks legitimate"
    )
    RiskLevel.SUSPICIOUS -> RiskUi(
        label = "Suspicious",
        icon = Icons.Outlined.Warning,
        color = WarningColorDark,
        containerColor = WarningColorDark.copy(alpha = 0.2f),
        headline = "Treat with caution"
    )
    RiskLevel.DANGEROUS -> RiskUi(
        label = "Dangerous",
        icon = Icons.Outlined.Cancel,
        color = DangerColorDark,
        containerColor = DangerColorDark.copy(alpha = 0.2f),
        headline = "It's almost certainly a scam"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenieResult(
    riskLevel: RiskLevel,
    confidence: Int, // 0-100
    explanation: String,
    onDismiss: () -> Unit
) {
    val riskUi = riskLevel.toUi()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Drag handle
            Surface(
                modifier = Modifier
                    .width(32.dp)
                    .height(4.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(2.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            ) {}

            // Risk badge + close button
            Row(
                modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Risk pill
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = riskUi.containerColor
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = riskUi.icon,
                            contentDescription = riskUi.label,
                            tint = riskUi.color,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = riskUi.label,
                            style = MaterialTheme.typography.labelMedium,
                            color = riskUi.color
                        )
                    }
                }

                // Close button
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            // Headline
            Text(
                text = riskUi.headline,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Confidence score
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(
                                color = riskUi.color,
                                fontSize = 36.sp,
                                fontWeight = MaterialTheme.typography.displayLarge.fontWeight
                            )) {
                                append("$confidence")
                            }
                            withStyle(SpanStyle(
                                color = riskUi.color,
                                fontSize = 16.sp
                            )) {
                                append("%")
                            }
                        }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "CONFIDENCE",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                }

                // Progress bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(confidence / 100f)
                            .fillMaxHeight()
                            .background(riskUi.color)
                    )
                }
            }

            // Explanation
            Text(
                text = explanation,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(8.dp))

            // Analyse another button
            PrimaryButton(
                text = "Analyse another",
                modifier = Modifier.fillMaxWidth(),
                onClick = onDismiss
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview
@Composable
private fun GenieResultSafePreview() {
    NortonTheme(darkTheme = true) {
        GenieResult(
            riskLevel = RiskLevel.SAFE,
            confidence = 96,
            explanation = "This message matches normal patterns from your bank. The sender domain is verified and links resolve to official addresses.",
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun GenieResultSuspiciousPreview() {
    NortonTheme(darkTheme = true) {
        GenieResult(
            riskLevel = RiskLevel.SUSPICIOUS,
            confidence = 78,
            explanation = "Several signals don't add up — the urgency language and shortened link are common in phishing, but the sender is otherwise unknown. Don't tap any links.",
            onDismiss = {},
        )
    }
}

@Preview
@Composable
private fun GenieResultDangerousPreview() {
    NortonTheme(darkTheme = true) {
        GenieResult(
            riskLevel = RiskLevel.DANGEROUS,
            confidence = 94,
            explanation = "The link domain mimics USPS but is registered to an unrelated party. USPS doesn't text about held packages this way. Delete and don't tap the link.",
            onDismiss = {},
        )
    }
}
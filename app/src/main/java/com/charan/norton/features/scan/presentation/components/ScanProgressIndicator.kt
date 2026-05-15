package com.charan.norton.features.scan.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.charan.norton.R

@Composable
fun ScanProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "scan_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing)
        ),
        label = "rotation"
    )

    // Initializing with current progress value - prevents reset to 0 on navigation recomposition
    val animatedProgress = remember { Animatable(progress) }

    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 600)
        )
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(160.dp)
    ) {
        // Track
        CircularProgressIndicator(
            progress = { 0f },
            modifier = Modifier.size(160.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
            strokeWidth = 10.dp,
            strokeCap = StrokeCap.Round
        )
        // Spinning arc
        CircularProgressIndicator(
            progress = { animatedProgress.value },
            modifier = Modifier
                .size(160.dp)
                .rotate(rotation),
            color = MaterialTheme.colorScheme.primary,
            trackColor = Color.Transparent,
            strokeWidth = 10.dp,
            strokeCap = StrokeCap.Round
        )
        // Center text
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${(animatedProgress.value * 100).toInt()}%",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (progress != 1f) {
                Text(
                    text = stringResource(R.string.status_scanning),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    letterSpacing = TextUnit(1.5f, TextUnitType.Sp)
                )
            }
        }
    }
}
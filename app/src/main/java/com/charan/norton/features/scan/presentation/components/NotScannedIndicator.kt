package com.charan.norton.features.scan.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.charan.norton.R
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun NotScannedIndicator(size: Dp = 200.dp) {
    val dashColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
    val iconBgColor = MaterialTheme.colorScheme.primaryContainer
    val iconColor = MaterialTheme.colorScheme.primary

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(size)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            drawDashedRing(
                color = dashColor,
                dashCount = 60,
                dashWidth = 5f,
                radius = this.size.minDimension / 2f - 4f
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(iconBgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Security,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = stringResource(R.string.home_not_scanned),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                fontWeight = FontWeight.Medium,
                letterSpacing = TextUnit(1.5f, TextUnitType.Sp)
            )
        }
    }
}


/**
* This ring UI is fully AI generated, after several attempts of modification, and validated
**/
private fun DrawScope.drawDashedRing(
    color: Color,
    dashCount: Int,
    dashWidth: Float,
    radius: Float
) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val angleStep = (2 * Math.PI / dashCount).toFloat()

    for (i in 0 until dashCount) {
        val angle = i * angleStep - (Math.PI / 2).toFloat()
        val dashLength = 14f  // all same length

        val innerRadius = radius - dashLength
        val outerRadius = radius

        val startX = center.x + innerRadius * cos(angle)
        val startY = center.y + innerRadius * sin(angle)
        val endX = center.x + outerRadius * cos(angle)
        val endY = center.y + outerRadius * sin(angle)

        drawLine(
            color = color,
            start = Offset(startX, startY),
            end = Offset(endX, endY),
            strokeWidth = dashWidth,
            cap = StrokeCap.Round
        )
    }
}
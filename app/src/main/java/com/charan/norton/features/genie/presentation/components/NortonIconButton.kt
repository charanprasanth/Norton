package com.charan.norton.features.genie.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NortonIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageVector: ImageVector,
    description: String,
    enabled: Boolean = true,
    tint: Color? = null,
) {
    val defaultColor = MaterialTheme.colorScheme.onSurface
    val activeColor = tint ?: defaultColor
    val finalColor = if (enabled) activeColor else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)

    IconButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .border(
                width = 1.dp,
                color = finalColor.copy(alpha = 0.5f),
                shape = CircleShape,
            )
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = description,
            tint = finalColor,
            modifier = Modifier.size(25.dp),
        )
    }
}

@Preview
@Composable
private fun IconButtonPreview() {
    NortonIconButton(
        onClick = { },
        imageVector = Icons.AutoMirrored.Filled.Send,
        description = "Analyse",
        modifier = Modifier.padding(top = 5.dp)
    )
}
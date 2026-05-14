package com.charan.norton.features.scan.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.charan.norton.common.theme.SafeColorDark
import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck

@Composable
fun CheckItemRow(check: ScanCheck) {
    val isActive = check.status != CheckStatus.PENDING
    val contentAlpha = if (isActive) 1f else 0.4f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(10.dp),
            color = when (check.status) {
                CheckStatus.SECURE -> SafeColorDark.copy(alpha = 0.15f)
                CheckStatus.SCANNING -> MaterialTheme.colorScheme.primaryContainer
                CheckStatus.WARNING -> MaterialTheme.colorScheme.errorContainer
                CheckStatus.PENDING -> MaterialTheme.colorScheme.surfaceVariant
                CheckStatus.COMPLETE -> MaterialTheme.colorScheme.secondaryContainer
            }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = when (check.title) {
                        "OS Version" -> Icons.Outlined.PhoneAndroid
                        "App Threats" -> Icons.Outlined.PhoneAndroid
                        "Wi-Fi Safety" -> Icons.Outlined.Wifi
                        else -> Icons.Outlined.Key
                    },
                    contentDescription = check.title,
                    tint = when (check.status) {
                        CheckStatus.SECURE -> SafeColorDark
                        CheckStatus.SCANNING -> MaterialTheme.colorScheme.primary
                        CheckStatus.WARNING -> MaterialTheme.colorScheme.error
                        CheckStatus.PENDING -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        CheckStatus.COMPLETE -> MaterialTheme.colorScheme.onSecondaryContainer
                    },
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = check.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = check.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = contentAlpha * 0.7f)
            )
        }

        StatusPill(status = check.status)
    }
}

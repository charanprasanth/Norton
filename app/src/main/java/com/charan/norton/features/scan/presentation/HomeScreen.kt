package com.charan.norton.features.scan.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charan.norton.common.components.PrimaryButton
import com.charan.norton.common.components.SubTitleText
import com.charan.norton.common.components.TitleText
import com.charan.norton.common.theme.NortonTheme
import com.charan.norton.features.scan.presentation.components.NotScannedIndicator

@Composable
fun HomeScreen(onScanClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 20.dp),
    ) {
        TitleText(text = "Good morning")

        SubTitleText(
            text = "Run a quick check to see how your device is doing today.",
            modifier = Modifier.padding(top = 5.dp, bottom = 24.dp)
        )

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                NotScannedIndicator()

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Tap to scan your device",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "We'll check OS health, app threats, Wi-Fi safety and passwords. Takes about 10 seconds.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Scan now",
            modifier = Modifier.fillMaxWidth(),
            onClick = onScanClick
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreviewLight() {
    NortonTheme(darkTheme = false, dynamicColor = false) {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreviewDark() {
    NortonTheme(darkTheme = true, dynamicColor = false) {
        HomeScreen()
    }
}
package com.charan.norton.features.genie.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.charan.norton.common.components.SubtitleText
import com.charan.norton.common.components.SuspiciousInputField
import com.charan.norton.common.components.TitleText
import com.charan.norton.common.theme.NortonTheme

@Composable
fun GenieScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TitleText(text = "Genie")
        SubtitleText(
            text = "Paste anything suspicious and I'll check it in seconds.",
            modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)
        )

        SuspiciousInputField(value = "Hello World", onValueChange = { it -> }, onPasteClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun GenieScreenPreviewLight() {
    NortonTheme(darkTheme = false) {
        GenieScreen()
    }
}

@Preview(showBackground = true)
@Composable
private fun GenieScreenPreviewDark() {
    NortonTheme(darkTheme = true) {
        GenieScreen()
    }
}
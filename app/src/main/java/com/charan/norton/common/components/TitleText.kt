package com.charan.norton.common.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.charan.norton.common.theme.NortonTheme

@Composable
fun TitleText(modifier: Modifier = Modifier, text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.displaySmall,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun TitleTextPreviewLight() {
    NortonTheme(darkTheme = false) {
        TitleText(text = "Genie")
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleTextPreviewDark() {
    NortonTheme(darkTheme = true) {
        TitleText(text = "Genie")
    }
}
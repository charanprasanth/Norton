package com.charan.norton.features.genie.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onAnalyseClicked: () -> Unit
) {
    val clipboardManager = LocalClipboardManager.current

    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(20.dp),
            )
            .padding(15.dp),
        horizontalAlignment = Alignment.End
    ) {
        BasicTextField(
            enabled = enabled,
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 180.dp, max = 250.dp) ,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = "Paste suspicious message, email, or URL…",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
                innerTextField()
            },
        )

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.padding(top = 5.dp)
        ) {
            NortonIconButton(
                imageVector = Icons.Outlined.ContentPaste,
                description = "Paste from clipboard",
                onClick = {
                    val clipboardText = clipboardManager.getText()?.text ?: ""
                    onValueChange(clipboardText)
                }
            )

            NortonIconButton(
                onClick = onAnalyseClicked,
                imageVector = Icons.AutoMirrored.Filled.Send,
                description = "Analyse",
                enabled = enabled && value.isNotEmpty(),
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 5.dp, start = 10.dp)
            )
        }
    }
}

@Preview
@Composable
private fun InputFieldPreview() {
    InputField(value = "", onValueChange = { }, onAnalyseClicked = {})
}
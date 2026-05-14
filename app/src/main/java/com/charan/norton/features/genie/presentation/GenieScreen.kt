package com.charan.norton.features.genie.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.charan.norton.common.components.SubTitleText
import com.charan.norton.common.components.TitleText
import com.charan.norton.common.theme.NortonTheme
import com.charan.norton.features.genie.presentation.components.ExampleChip
import com.charan.norton.features.genie.presentation.components.GenieAnalysing
import com.charan.norton.features.genie.presentation.components.GenieResult
import com.charan.norton.features.genie.presentation.components.InputField

@Composable
fun GenieScreen(
    viewModel: GenieViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    GenieContent(state = state, onAction = viewModel::onAction)
}

@Composable
fun GenieContent(
    state: GenieState,
    onAction: (GenieAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(vertical = 20.dp, horizontal = 15.dp)
    ) {
        TitleText(text = "Genie")

        SubTitleText(
            text = "Paste anything suspicious and I'll check it in seconds.",
            modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)
        )

        InputField(
            value = state.inputText,
            onValueChange = { onAction(GenieAction.OnInputTextChange(it)) },
            enabled = !state.isLoading,
            onAnalyseClicked = {
                onAction(GenieAction.OnAnalyse)
            }
        )

        AnimatedContent(
            targetState = state.isLoading,
            transitionSpec = {
                fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
            },
        ) { isLoading ->
            if (isLoading) {
                GenieAnalysing(
                    modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
                )
            } else {
                Column {
                    Text(
                        text = "TRY AN EXAMPLE",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(top = 15.dp, bottom = 10.dp)
                    )
                    genieExamples.forEach { (label, text) ->
                        ExampleChip(
                            label = label,
                            text = text,
                            modifier = Modifier.padding(bottom = 10.dp),
                            onClick = { onAction(GenieAction.OnInputTextChange(text)) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }

    state.result?.let { result ->
        GenieResult(
            riskLevel = result.riskLevel,
            confidence = result.confidence,
            explanation = result.explanation,
            onDismiss = { onAction(GenieAction.OnDismissResult) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun GenieScreenPreviewLight() {
    NortonTheme(darkTheme = false) {
        GenieScreen()
    }
}

@Preview
@Composable
private fun GenieScreenPreviewDark() {
    NortonTheme(darkTheme = true) {
        GenieScreen()
    }
}
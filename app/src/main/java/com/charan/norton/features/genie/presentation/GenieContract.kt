package com.charan.norton.features.genie.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.charan.norton.R
import com.charan.norton.features.genie.domain.model.ScamResult

data class GenieState(
    val inputText: String = "",
    val isLoading: Boolean = false,
    val result: ScamResult? = null,
)

sealed class GenieAction {
    data class OnInputTextChange(val text: String) : GenieAction()
    data object OnAnalyse : GenieAction()
    data object OnDismissResult : GenieAction()
}

@Composable
fun genieExamples(): List<Pair<String, String>> = listOf(
    stringResource(R.string.genie_example_label_sms) to stringResource(R.string.genie_example_text_sms),
    stringResource(R.string.genie_example_label_email) to stringResource(R.string.genie_example_text_email),
    stringResource(R.string.genie_example_label_url) to stringResource(R.string.genie_example_text_url),
)
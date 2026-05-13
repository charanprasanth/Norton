package com.charan.norton.features.genie.presentation

import com.charan.norton.features.genie.domain.model.ScamResult

data class GenieState(
    val inputText: String = "",
    val isLoading: Boolean = false,
    val result: ScamResult? = null,
)

sealed class GenieAction {
    data class OnInputTextChange(val text: String) : GenieAction()
    object OnAnalyse : GenieAction()
    object OnDismissResult : GenieAction()
}

sealed interface GenieEvent

val genieExamples = listOf(
    "SMS" to "USPS: Your package is held. Confirm delivery address within 24h: usps-track.help/x29f",
    "EMAIL" to "Your Amazon order #112-3456789 has shipped. Track at amazon.com/orders",
    "URL" to "paypa1-secure-login.com/account/verify",
)
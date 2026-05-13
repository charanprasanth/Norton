package com.charan.norton.features.genie.presentation

data class GenieState(
    val inputText: String = ""
)

sealed interface GenieAction {
    data class OnInputTextChange(val text: String) : GenieAction
}

sealed interface GenieEvent

val genieExamples = listOf(
    "SMS" to "USPS: Your package is held at our facility. Confirm delivery address within 24h: usps-track.help/x29f",
    "EMAIL" to "Bank of America — unusual sign-in detected. Reset your password immediately: secure-boa.com/verify",
    "URL" to "paypa1-secure-login.com/account/verify This site asks for your PayPal credentials."
)
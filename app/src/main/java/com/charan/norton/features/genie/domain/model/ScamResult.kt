package com.charan.norton.features.genie.domain.model

data class ScamResult(
    val riskLevel: RiskLevel,
    val confidence: Int,
    val explanation: String,
)
package com.charan.norton.features.scan.domain.model

data class ScanCheck(
    val title: String,
    val description: String,
    val status: CheckStatus
)
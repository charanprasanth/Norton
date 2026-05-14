package com.charan.norton.features.scan.domain.model

data class SecurityScore(
    val overallScore: Int,
    val checks: List<ScanCheck>
)
package com.charan.norton.features.scan.presentation

import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.domain.model.SecurityScore

data class ScanState(
    val checks: List<ScanCheck> = emptyList(),
    val currentCheckIndex: Int = 0,
    val progress: Float = 0f,
    val isScanning: Boolean = false,
    val isComplete: Boolean = false,
    val scanResult: SecurityScore? = null
)

sealed class ScanAction {
    data object StartScan : ScanAction() //singleton and data-like behaviour -> better better toString(), structural equality/hashCode
}

sealed interface ScanEvent
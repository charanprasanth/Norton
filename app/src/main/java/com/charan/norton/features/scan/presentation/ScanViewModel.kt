package com.charan.norton.features.scan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.domain.usecase.RunScanUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val runScan: RunScanUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ScanState())
    val state = _state.asStateFlow()

    /**
     * Routes UI actions to the appropriate handler.
     */
    fun onAction(action: ScanAction) {
        when (action) {
            is ScanAction.StartScan -> startScan(action.scanningDescriptions)
        }
    }

    /**
     * Runs the scan, animating each check sequentially with a delay between steps.
     */
    private fun startScan(scanningDescriptions: List<String>) {
        if (_state.value.isScanning || _state.value.isComplete) return

        viewModelScope.launch {
            val securityScore = runScan()
            val total = securityScore.checks.size

            val initialChecks = securityScore.checks.map { check ->
                ScanCheck(check.title, "Waiting to scan...", CheckStatus.PENDING)
            }

            _state.update {
                it.copy(checks = initialChecks, isScanning = true, currentCheckIndex = 0, progress = 0f)
            }

            securityScore.checks.forEachIndexed { index, _ ->
                _state.update { state ->
                    state.copy(
                        currentCheckIndex = index,
                        progress = index / total.toFloat(),
                        checks = state.checks.mapIndexed { i, check ->
                            when {
                                i < index -> check.copy(
                                    status = CheckStatus.COMPLETE,
                                    description = securityScore.checks[i].description
                                )
                                i == index -> check.copy(
                                    status = CheckStatus.SCANNING,
                                    description = scanningDescriptions.getOrElse(index) { "" }
                                )
                                else -> check
                            }
                        }
                    )
                }
                delay(2000L)
            }

            _state.update { state ->
                state.copy(
                    isScanning = false,
                    isComplete = true,
                    progress = 1f,
                    scanResult = securityScore,
                    checks = state.checks.mapIndexed { i, check ->
                        check.copy(
                            status = CheckStatus.COMPLETE,
                            description = securityScore.checks[i].description
                        )
                    }
                )
            }
        }
    }
}
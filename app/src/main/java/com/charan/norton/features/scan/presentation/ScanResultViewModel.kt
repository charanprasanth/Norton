package com.charan.norton.features.scan.presentation

import androidx.lifecycle.ViewModel
import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.domain.repository.ScanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScanResultViewModel @Inject constructor(
    repository: ScanRepository
) : ViewModel() {

    val scanResult: SecurityScore? = repository.getLastScanResult()
}
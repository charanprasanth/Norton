package com.charan.norton.features.scan.domain.usecase

import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.domain.repository.ScanRepository
import javax.inject.Inject

class RunScanUseCase @Inject constructor(
    private val repository: ScanRepository
) {
    suspend operator fun invoke(): SecurityScore = repository.runScan()
}
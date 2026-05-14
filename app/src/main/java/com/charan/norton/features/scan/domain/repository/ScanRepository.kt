package com.charan.norton.features.scan.domain.repository

import com.charan.norton.features.scan.domain.model.SecurityScore

interface ScanRepository {

    suspend fun runScan(): SecurityScore

    fun getLastScanResult(): SecurityScore?
}
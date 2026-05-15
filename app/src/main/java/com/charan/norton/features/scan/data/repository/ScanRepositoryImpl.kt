package com.charan.norton.features.scan.data.repository

import com.charan.norton.features.scan.data.datasource.MockScanDataSource
import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.domain.repository.ScanRepository
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val dataSource: MockScanDataSource
) : ScanRepository {

    /**
     * Fetches a fresh security score from the data source.
     */
    override suspend fun runScan(): SecurityScore = dataSource.fetchSecurityScore()

    /**
     * Returns the most recently completed scan result, or null if none exists.
     */
    override fun getLastScanResult(): SecurityScore? = dataSource.getLastResult()
}
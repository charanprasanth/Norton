package com.charan.norton.features.scan.data.repository

import com.charan.norton.features.scan.data.datasource.MockScanDataSource
import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.domain.repository.ScanRepository
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val dataSource: MockScanDataSource
) : ScanRepository {

    override suspend fun runScan(): SecurityScore = dataSource.fetchSecurityScore()

    override fun getLastScanResult(): SecurityScore? = dataSource.getLastResult()
}
package com.charan.norton.features.scan.data.datasource

import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.domain.model.SecurityScore
import javax.inject.Inject

class MockScanDataSource @Inject constructor() {

    private var lastResult: SecurityScore? = null

    fun getLastResult(): SecurityScore? = lastResult

    fun fetchSecurityScore(): SecurityScore {
        val checks = listOf(
            ScanCheck("OS Version", "Android 14 · up to date", CheckStatus.SECURE),
            ScanCheck("App Threats", "142 apps reviewed · none flagged", CheckStatus.SECURE),
            ScanCheck("Wi-Fi Safety", "Open network · consider VPN", CheckStatus.WARNING),
            ScanCheck("Password Strength", "2 reused passwords found", CheckStatus.SECURE)
        )
        val score = (checks.count { it.status == CheckStatus.SECURE } * 100) / checks.size
        return SecurityScore(overallScore = score, checks = checks).also { lastResult = it }
    }
}
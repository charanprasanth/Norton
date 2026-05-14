package com.charan.norton.features.scan.presentation

import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.domain.repository.ScanRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ScanResultViewModelTest {

    private val repository: ScanRepository = mockk()

    /**
     * Purpose: Verify scanResult is populated with the last scan stored in the repository.
     *
     * Author: charanprasanth
     */
    @Test
    fun `scanResult reflects last scan from repository`() {
        val score = SecurityScore(
            overallScore = 78,
            checks = listOf(
                ScanCheck("OS Version", "Android 14 · up to date", CheckStatus.SECURE),
                ScanCheck("App Threats", "142 apps reviewed · none flagged", CheckStatus.SECURE),
                ScanCheck("Wi-Fi Safety", "Open network · consider VPN", CheckStatus.WARNING),
                ScanCheck("Password Strength", "2 reused passwords found", CheckStatus.WARNING)
            )
        )
        every { repository.getLastScanResult() } returns score
        val viewModel = ScanResultViewModel(repository)
        assertEquals(score, viewModel.scanResult)
    }

    /**
     * Purpose: Verify scanResult is null when no scan has been run yet.
     *
     * Author: charanprasanth
     */
    @Test
    fun `scanResult is null when no previous scan exists`() {
        every { repository.getLastScanResult() } returns null
        val viewModel = ScanResultViewModel(repository)
        assertNull(viewModel.scanResult)
    }

    /**
     * Purpose: Verify the overall score from the last scan is surfaced correctly.
     *
     * Author: charanprasanth
     */
    @Test
    fun `scanResult exposes correct overall score`() {
        val score = SecurityScore(overallScore = 50, checks = emptyList())
        every { repository.getLastScanResult() } returns score
        val viewModel = ScanResultViewModel(repository)
        assertEquals(50, viewModel.scanResult?.overallScore)
    }
}
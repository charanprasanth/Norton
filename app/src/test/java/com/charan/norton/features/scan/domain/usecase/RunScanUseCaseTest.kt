package com.charan.norton.features.scan.domain.usecase

import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.domain.repository.ScanRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class RunScanUseCaseTest {

    private val repository: ScanRepository = mockk()
    private val useCase = RunScanUseCase(repository)

    private val stubScore = SecurityScore(
        overallScore = 78,
        checks = listOf(
            ScanCheck("OS Version", "Android 14 · up to date", CheckStatus.SECURE),
            ScanCheck("App Threats", "142 apps reviewed · none flagged", CheckStatus.SECURE),
            ScanCheck("Wi-Fi Safety", "Open network · consider VPN", CheckStatus.WARNING),
            ScanCheck("Password Strength", "2 reused passwords found", CheckStatus.WARNING)
        )
    )

    /**
     * Purpose: Verify use case returns the score provided by the repository.
     *
     * Author: charanprasanth
     */
    @Test
    fun `invoke returns score from repository`() = runTest {
        coEvery { repository.runScan() } returns stubScore
        val result = useCase()
        assertEquals(stubScore, result)
    }

    /**
     * Purpose: Verify use case propagates exceptions thrown by the repository.
     *
     * Author: charanprasanth
     */
    @Test
    fun `invoke propagates repository exception`() = runTest {
        coEvery { repository.runScan() } throws RuntimeException("Network error")
        val exception = runCatching { useCase() }.exceptionOrNull()
        assertEquals("Network error", exception?.message)
    }
}
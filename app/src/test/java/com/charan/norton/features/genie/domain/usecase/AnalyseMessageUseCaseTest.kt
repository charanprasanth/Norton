package com.charan.norton.features.genie.domain.usecase

import com.charan.norton.common.network.NetworkChecker
import com.charan.norton.features.genie.domain.model.RiskLevel
import com.charan.norton.features.genie.domain.model.ScamResult
import com.charan.norton.features.genie.domain.repository.GenieRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AnalyseMessageUseCaseTest {

    private val repository: GenieRepository = mockk()
    private val networkChecker: NetworkChecker = mockk()
    private val useCase = AnalyseMessageUseCase(repository, networkChecker)

    @BeforeEach
    fun setUp() {
        every { networkChecker.isConnected() } returns true
    }

    /**
     * Verify DANGEROUS risk responses are parsed correctly.
     *
     * Author: charanprasanth
     */
    @Test
    fun `dangerous risk maps correctly`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "risk: DANGEROUS\nconfidence: 90\nreason: Fake USPS domain detected."
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.DANGEROUS, 90, "Fake USPS domain detected."), result)
    }

    /**
     * Verify SUSPICIOUS risk responses are parsed correctly.
     *
     * Author: charanprasanth
     */
    @Test
    fun `suspicious risk maps correctly`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "risk: SUSPICIOUS\nconfidence: 60\nreason: Shortened URL from unknown sender."
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.SUSPICIOUS, 60, "Shortened URL from unknown sender."), result)
    }

    /**
     * Verify SAFE risk responses are parsed correctly.
     *
     * Author: charanprasanth
     */
    @Test
    fun `safe risk maps correctly`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "risk: SAFE\nconfidence: 95\nreason: Verified Amazon domain."
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.SAFE, 95, "Verified Amazon domain."), result)
    }

    /**
     * Verify UNKNOWN risk responses are handled correctly.
     *
     * AI-generated and validated
     */
    @Test
    fun `unknown risk maps to UNKNOWN`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "risk: UNKNOWN\nconfidence: 0\nreason: Could not determine."
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.UNKNOWN, 0, "Could not determine."), result)
    }

    /**
     * Verify risk parsing remains case-insensitive.
     *
     * Author: charanprasanth (AI assisted)
     */
    @Test
    fun `risk matching is case insensitive`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "risk: dangerous\nconfidence: 88\nreason: Spoofed PayPal domain."
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.DANGEROUS, 88, "Spoofed PayPal domain."), result)
    }

    /**
     * Verify confidence values above 100 are safely clamped.
     *
     * AI-generated and validated
     */
    @Test
    fun `confidence is clamped to 100 when over 100`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "risk: SAFE\nconfidence: 150\nreason: Legitimate site."
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.SAFE, 100, "Legitimate site."), result)
    }

    /**
     * Verify zero confidence always maps to UNKNOWN risk.
     *
     * AI-generated and validated
     */
    @Test
    fun `confidence of zero maps to UNKNOWN risk`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "risk: SAFE\nconfidence: 0\nreason: Could not determine."
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.UNKNOWN, 0, "Could not determine."), result)
    }

    /**
     * Verify malformed responses return fallback results safely.
     *
     * AI-generated and validated
     */
    @Test
    fun `invalid response returns fallback result`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns "not valid at all"
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.UNKNOWN, 0, "Unable to determine risk."), result)
    }

    /**
     * Verify empty responses return fallback results safely.
     *
     * AI-generated and validated
     */
    @Test
    fun `empty response returns fallback result`() = runTest {
        coEvery { repository.analyseMessage(any()) } returns ""
        val result = useCase("")
        assertEquals(ScamResult(RiskLevel.UNKNOWN, 0, "Unable to determine risk."), result)
    }
}
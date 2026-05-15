package com.charan.norton.features.scan.presentation

import com.charan.norton.features.scan.domain.model.CheckStatus
import com.charan.norton.features.scan.domain.model.ScanCheck
import com.charan.norton.features.scan.domain.model.SecurityScore
import com.charan.norton.features.scan.domain.usecase.RunScanUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScanViewModelTest {

    private val runScan: RunScanUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ScanViewModel

    private val stubScore = SecurityScore(
        overallScore = 78,
        checks = listOf(
            ScanCheck("OS Version", "Android 14 · up to date", CheckStatus.SECURE),
            ScanCheck("App Threats", "142 apps reviewed · none flagged", CheckStatus.SECURE),
            ScanCheck("Wi-Fi Safety", "Open network · consider VPN", CheckStatus.WARNING),
            ScanCheck("Password Strength", "2 reused passwords found", CheckStatus.WARNING)
        )
    )

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { runScan() } returns stubScore
        viewModel = ScanViewModel(runScan)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Verify ViewModel starts with a clean default state before any action is dispatched.
     */
    @Test
    fun `initial state is default`() {
        val state = viewModel.state.value
        assertTrue(state.checks.isEmpty())
        assertFalse(state.isScanning)
        assertFalse(state.isComplete)
        assertEquals(0f, state.progress)
        assertNull(state.scanResult)
    }

    /**
     * Verify isScanning is set to true as soon as the scan coroutine starts.
     */
    @Test
    fun `StartScan sets isScanning true after launch`() = runTest(testDispatcher) {
        viewModel.onAction(ScanAction.StartScan(emptyList()))
        advanceTimeBy(1)
        assertTrue(viewModel.state.value.isScanning)
    }

    /**
     * Verify isComplete is true and isScanning is false once the scan finishes.
     */
    @Test
    fun `StartScan sets isComplete true and isScanning false when done`() = runTest(testDispatcher) {
        viewModel.onAction(ScanAction.StartScan(emptyList()))
        advanceUntilIdle()
        assertTrue(viewModel.state.value.isComplete)
        assertFalse(viewModel.state.value.isScanning)
    }

    /**
     * Verify the first check transitions to SCANNING while the rest stay PENDING.
     */
    @Test
    fun `StartScan sets first check to SCANNING and rest to PENDING`() = runTest(testDispatcher) {
        viewModel.onAction(ScanAction.StartScan(emptyList()))
        advanceTimeBy(1)
        val checks = viewModel.state.value.checks
        assertEquals(4, checks.size)
        assertEquals(CheckStatus.SCANNING, checks[0].status)
        assertEquals(CheckStatus.PENDING, checks[1].status)
        assertEquals(CheckStatus.PENDING, checks[2].status)
        assertEquals(CheckStatus.PENDING, checks[3].status)
    }

    /**
     * Verify progress reaches exactly 1f when the scan completes.
     */
    @Test
    fun `StartScan sets progress to 1f when done`() = runTest(testDispatcher) {
        viewModel.onAction(ScanAction.StartScan(emptyList()))
        advanceUntilIdle()
        assertEquals(1f, viewModel.state.value.progress)
    }

    /**
     * Verify scanResult is populated with the SecurityScore from the use case on completion.
     */
    @Test
    fun `StartScan sets scanResult with data from use case`() = runTest(testDispatcher) {
        viewModel.onAction(ScanAction.StartScan(emptyList()))
        advanceUntilIdle()
        val result = viewModel.state.value.scanResult
        assertNotNull(result)
        assertEquals(78, result?.overallScore)
        assertEquals(stubScore.checks, result?.checks)
    }
}

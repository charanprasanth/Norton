package com.charan.norton.features.genie.presentation

import com.charan.norton.features.genie.domain.model.RiskLevel
import com.charan.norton.features.genie.domain.model.ScamResult
import com.charan.norton.features.genie.domain.usecase.AnalyseMessageUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GenieViewModelTest {

    private val analyseMessage: AnalyseMessageUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: GenieViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = GenieViewModel(analyseMessage)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Verify input text updates correctly in ViewModel state.
     *
     * Author: charanprasanth
     */
    @Test
    fun `OnInputTextChange updates inputText in state`() = runTest(testDispatcher) {
        viewModel.onAction(GenieAction.OnInputTextChange("hello world"))
        assertEquals("hello world", viewModel.state.value.inputText)
    }

    /**
     * Verify loading state changes correctly during analysis.
     *
     * Author: charanprasanth
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `OnAnalyse sets isLoading true then false`() = runTest(testDispatcher) {
        coEvery { analyseMessage(any()) } returns ScamResult(RiskLevel.SAFE, 90, "Safe.")
        viewModel.onAction(GenieAction.OnInputTextChange("test message"))
        viewModel.onAction(GenieAction.OnAnalyse)
        assertTrue(viewModel.state.value.isLoading)
        advanceUntilIdle()
        assertFalse(viewModel.state.value.isLoading)
    }

    /**
     * Verify successful analysis updates result state correctly.
     *
     * Author: charanprasanth
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `OnAnalyse sets result on success`() = runTest(testDispatcher) {
        val expected = ScamResult(RiskLevel.DANGEROUS, 92, "Phishing link.")
        coEvery { analyseMessage(any()) } returns expected
        viewModel.onAction(GenieAction.OnInputTextChange("test message"))
        viewModel.onAction(GenieAction.OnAnalyse)
        advanceUntilIdle()
        assertEquals(expected, viewModel.state.value.result)
    }

    /**
     * Verify analyse action is ignored for empty input.
     *
     * AI-generated and validated
     */
    @Test
    fun `OnAnalyse does nothing when input is empty`() = runTest(testDispatcher) {
        viewModel.onAction(GenieAction.OnAnalyse)
        assertFalse(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.result)
    }

    /**
     * Verify loading state resets correctly when analysis fails.
     *
     * AI-generated and validated
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `OnAnalyse clears isLoading on exception`() = runTest(testDispatcher) {
        coEvery { analyseMessage(any()) } throws RuntimeException("Network error")
        viewModel.onAction(GenieAction.OnInputTextChange("test message"))
        viewModel.onAction(GenieAction.OnAnalyse)
        assertTrue(viewModel.state.value.isLoading)
        advanceUntilIdle()
        assertFalse(viewModel.state.value.isLoading)
        assertNull(viewModel.state.value.result)
    }

    /**
     * Verify dismiss action clears analysis result from state.
     *
     * AI-generated and validated
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `OnDismissResult clears result from state`() = runTest(testDispatcher) {
        val result = ScamResult(RiskLevel.DANGEROUS, 90, "Dangerous.")
        coEvery { analyseMessage(any()) } returns result
        viewModel.onAction(GenieAction.OnInputTextChange("test message"))
        viewModel.onAction(GenieAction.OnAnalyse)
        advanceUntilIdle()
        viewModel.onAction(GenieAction.OnDismissResult)
        assertNull(viewModel.state.value.result)
    }
}
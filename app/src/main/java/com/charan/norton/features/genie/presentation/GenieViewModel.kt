package com.charan.norton.features.genie.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.charan.norton.features.genie.domain.usecase.AnalyseMessageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenieViewModel @Inject constructor(
    private val analyseMessage: AnalyseMessageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GenieState())
    val state = _state.asStateFlow()

    /**
     * Routes UI actions to the appropriate handler.
     */
    fun onAction(action: GenieAction) {
        when (action) {
            is GenieAction.OnInputTextChange -> _state.update { it.copy(inputText = action.text) }
            is GenieAction.OnAnalyse -> analyse()
            is GenieAction.OnDismissResult -> _state.update { it.copy(result = null) }
        }
    }

    /**
     * Submits the current input to the use case and updates loading and result state.
     */
    private fun analyse() {
        val input = _state.value.inputText.trim()
        if (input.isEmpty()) return

        _state.update { it.copy(isLoading = true, result = null) }

        viewModelScope.launch {
            try {
                val result = analyseMessage(input)
                _state.update { it.copy(isLoading = false, result = result) }
            } catch (e: Exception) {
                Log.e("SCAM_RESULT", "Failed: ${e.message}")
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}
package com.charan.norton.features.genie.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GenieViewModel : ViewModel() {

    private val _state = MutableStateFlow(GenieState())
    val state = _state.asStateFlow()

    fun onAction(action: GenieAction) {
        when (action) {
            is GenieAction.OnInputTextChange -> _state.update { it.copy(inputText = action.text) }
        }
    }
}
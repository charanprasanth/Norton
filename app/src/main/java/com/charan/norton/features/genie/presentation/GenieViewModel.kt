package com.charan.norton.features.genie.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GenieViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(GenieState())
    val state = _state.asStateFlow()

    fun onAction(action: GenieAction) {
        when (action) {
            is GenieAction.OnInputTextChange -> _state.update { it.copy(inputText = action.text) }
        }
    }
}
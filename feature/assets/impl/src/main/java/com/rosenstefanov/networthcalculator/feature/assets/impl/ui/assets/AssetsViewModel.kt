package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssetsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<AssetsUiState>(AssetsUiState.Loading)
    val uiState: StateFlow<AssetsUiState> = _uiState.asStateFlow()

    private val _effects = Channel<AssetsEffect>(Channel.BUFFERED)
    val effects: Flow<AssetsEffect> = _effects.receiveAsFlow()

    init {
        load()
    }

    fun onIntent(intent: AssetsIntent) {
        when (intent) {
            AssetsIntent.SettingsClicked -> emitEffect(AssetsEffect.NavigateToSettings)
            AssetsIntent.AddAccountClicked -> emitEffect(AssetsEffect.NavigateToAddAccount)
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = AssetsUiState.Loading
            delay(LOAD_DELAY_MS)
            _uiState.value = AssetsUiState.Content(
                total = "$412,300",
                deltaText = "+3.1%",
                isGain = true,
                summary = "7 holdings · 5 categories",
            )
        }
    }

    private fun emitEffect(effect: AssetsEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }

    private companion object {
        const val LOAD_DELAY_MS = 300L
    }
}

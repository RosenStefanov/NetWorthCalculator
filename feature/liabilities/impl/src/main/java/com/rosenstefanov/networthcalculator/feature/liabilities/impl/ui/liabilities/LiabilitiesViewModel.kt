package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesUiState
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
class LiabilitiesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<LiabilitiesUiState>(LiabilitiesUiState.Loading)
    val uiState: StateFlow<LiabilitiesUiState> = _uiState.asStateFlow()

    private val _effects = Channel<LiabilitiesEffect>(Channel.BUFFERED)
    val effects: Flow<LiabilitiesEffect> = _effects.receiveAsFlow()

    init {
        load()
    }

    fun onIntent(intent: LiabilitiesIntent) {
        when (intent) {
            LiabilitiesIntent.SettingsClicked -> emitEffect(LiabilitiesEffect.NavigateToSettings)
            LiabilitiesIntent.AddAccountClicked -> emitEffect(LiabilitiesEffect.NavigateToAddAccount)
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = LiabilitiesUiState.Loading
            delay(LOAD_DELAY_MS)
            _uiState.value = LiabilitiesUiState.Content(
                total = "$127,550",
                deltaText = "−1.8%",
                isGain = false,
                summary = "4 debts · 4 categories",
            )
        }
    }

    private fun emitEffect(effect: LiabilitiesEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }

    private companion object {
        const val LOAD_DELAY_MS = 300L
    }
}

package com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsEffect
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsIntent
import com.rosenstefanov.networthcalculator.feature.settings.impl.ui.settings.models.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    // Pre-filled with placeholder preferences until the data layer is wired up.
    private val _uiState = MutableStateFlow(SettingsUiState(name = "Rosen"))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    private val _effects = Channel<SettingsEffect>(Channel.BUFFERED)
    val effects: Flow<SettingsEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.NameChanged -> _uiState.value = _uiState.value.copy(name = intent.name)
            is SettingsIntent.ThemeSelected -> _uiState.value = _uiState.value.copy(theme = intent.theme)
            SettingsIntent.CurrencyClicked -> _uiState.value = _uiState.value.copy(showCurrencyPicker = true)
            is SettingsIntent.CurrencySelected ->
                _uiState.value = _uiState.value.copy(currency = intent.code, showCurrencyPicker = false)
            SettingsIntent.CurrencyPickerDismissed -> _uiState.value = _uiState.value.copy(showCurrencyPicker = false)
            SettingsIntent.BackClicked -> viewModelScope.launch { _effects.send(SettingsEffect.NavigateBack) }
            SettingsIntent.RateClicked -> Unit
            SettingsIntent.PrivacyClicked -> Unit
        }
    }
}

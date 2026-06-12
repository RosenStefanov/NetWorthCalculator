package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityUiState
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
class AddLiabilityViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<AddLiabilityUiState>(AddLiabilityUiState.Content())
    val uiState: StateFlow<AddLiabilityUiState> = _uiState.asStateFlow()

    private val _effects = Channel<AddLiabilityEffect>(Channel.BUFFERED)
    val effects: Flow<AddLiabilityEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: AddLiabilityIntent) {
        when (intent) {
            is AddLiabilityIntent.NameChanged -> {
                val current = _uiState.value
                if (current is AddLiabilityUiState.Content) {
                    _uiState.value = current.copy(name = intent.name)
                }
            }
            AddLiabilityIntent.SaveClicked -> emitEffect(AddLiabilityEffect.NavigateBack)
            AddLiabilityIntent.CloseClicked -> emitEffect(AddLiabilityEffect.NavigateBack)
        }
    }

    private fun emitEffect(effect: AddLiabilityEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

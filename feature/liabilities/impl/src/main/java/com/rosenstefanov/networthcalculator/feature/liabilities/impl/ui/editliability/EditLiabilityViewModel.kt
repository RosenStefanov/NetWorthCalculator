package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityUiState
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
class EditLiabilityViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<EditLiabilityUiState>(EditLiabilityUiState.Content)
    val uiState: StateFlow<EditLiabilityUiState> = _uiState.asStateFlow()

    private val _effects = Channel<EditLiabilityEffect>(Channel.BUFFERED)
    val effects: Flow<EditLiabilityEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: EditLiabilityIntent) {
        when (intent) {
            EditLiabilityIntent.CloseClicked -> emitEffect(EditLiabilityEffect.NavigateBack)
        }
    }

    private fun emitEffect(effect: EditLiabilityEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

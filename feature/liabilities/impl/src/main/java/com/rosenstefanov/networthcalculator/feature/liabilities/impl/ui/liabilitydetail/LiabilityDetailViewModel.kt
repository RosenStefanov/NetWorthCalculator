package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailUiState
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
class LiabilityDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<LiabilityDetailUiState>(LiabilityDetailUiState.Content())
    val uiState: StateFlow<LiabilityDetailUiState> = _uiState.asStateFlow()

    private val _effects = Channel<LiabilityDetailEffect>(Channel.BUFFERED)
    val effects: Flow<LiabilityDetailEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: LiabilityDetailIntent) {
        when (intent) {
            LiabilityDetailIntent.CloseClicked -> emitEffect(LiabilityDetailEffect.NavigateBack)
            is LiabilityDetailIntent.RangeSelected -> updateContent { it.copy(range = intent.range) }
            LiabilityDetailIntent.EditClicked -> emitEffect(LiabilityDetailEffect.NavigateToEdit)
            LiabilityDetailIntent.DeleteClicked -> updateContent { it.copy(showDeleteDialog = true) }
            LiabilityDetailIntent.DeleteDismissed -> updateContent { it.copy(showDeleteDialog = false) }
            LiabilityDetailIntent.DeleteConfirmed -> emitEffect(LiabilityDetailEffect.NavigateBack)
        }
    }

    private inline fun updateContent(
        transform: (LiabilityDetailUiState.Content) -> LiabilityDetailUiState.Content,
    ) {
        val current = _uiState.value
        if (current is LiabilityDetailUiState.Content) {
            _uiState.value = transform(current)
        }
    }

    private fun emitEffect(effect: LiabilityDetailEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

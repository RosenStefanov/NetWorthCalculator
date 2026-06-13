package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailUiState
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
class AssetDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<AssetDetailUiState>(AssetDetailUiState.Content())
    val uiState: StateFlow<AssetDetailUiState> = _uiState.asStateFlow()

    private val _effects = Channel<AssetDetailEffect>(Channel.BUFFERED)
    val effects: Flow<AssetDetailEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: AssetDetailIntent) {
        when (intent) {
            AssetDetailIntent.CloseClicked -> emitEffect(AssetDetailEffect.NavigateBack)
            is AssetDetailIntent.RangeSelected -> updateContent { it.copy(range = intent.range) }
            AssetDetailIntent.EditClicked -> emitEffect(AssetDetailEffect.NavigateToEdit)
            AssetDetailIntent.DeleteClicked -> updateContent { it.copy(showDeleteDialog = true) }
            AssetDetailIntent.DeleteDismissed -> updateContent { it.copy(showDeleteDialog = false) }
            AssetDetailIntent.DeleteConfirmed -> emitEffect(AssetDetailEffect.NavigateBack)
        }
    }

    private inline fun updateContent(transform: (AssetDetailUiState.Content) -> AssetDetailUiState.Content) {
        val current = _uiState.value
        if (current is AssetDetailUiState.Content) {
            _uiState.value = transform(current)
        }
    }

    private fun emitEffect(effect: AssetDetailEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetUiState
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
class EditAssetViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<EditAssetUiState>(EditAssetUiState.Content)
    val uiState: StateFlow<EditAssetUiState> = _uiState.asStateFlow()

    private val _effects = Channel<EditAssetEffect>(Channel.BUFFERED)
    val effects: Flow<EditAssetEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: EditAssetIntent) {
        when (intent) {
            EditAssetIntent.CloseClicked -> emitEffect(EditAssetEffect.NavigateBack)
        }
    }

    private fun emitEffect(effect: EditAssetEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

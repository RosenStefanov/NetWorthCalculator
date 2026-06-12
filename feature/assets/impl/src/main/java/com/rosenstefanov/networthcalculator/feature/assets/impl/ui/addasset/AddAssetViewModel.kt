package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.core.common.CurrencyFormatter
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.addasset.models.AddAssetUiState
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
class AddAssetViewModel @Inject constructor(
    private val currencyFormatter: CurrencyFormatter,
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddAssetUiState>(AddAssetUiState.Content())
    val uiState: StateFlow<AddAssetUiState> = _uiState.asStateFlow()

    private val _effects = Channel<AddAssetEffect>(Channel.BUFFERED)
    val effects: Flow<AddAssetEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: AddAssetIntent) {
        when (intent) {
            is AddAssetIntent.NameChanged -> {
                val current = _uiState.value
                if (current is AddAssetUiState.Content) {
                    _uiState.value = current.copy(name = intent.name)
                }
            }
            is AddAssetIntent.AmountChanged -> {
                val current = _uiState.value
                if (current is AddAssetUiState.Content) {
                    _uiState.value = current.copy(amount = currencyFormatter.format(intent.amount))
                }
            }
            is AddAssetIntent.CategorySelected -> {
                val current = _uiState.value
                if (current is AddAssetUiState.Content) {
                    _uiState.value = current.copy(category = intent.category)
                }
            }
            is AddAssetIntent.DescriptionChanged -> {
                val current = _uiState.value
                if (current is AddAssetUiState.Content) {
                    _uiState.value = current.copy(description = intent.description)
                }
            }
            AddAssetIntent.SaveClicked -> emitEffect(AddAssetEffect.NavigateBack)
            AddAssetIntent.CloseClicked -> emitEffect(AddAssetEffect.NavigateBack)
        }
    }

    private fun emitEffect(effect: AddAssetEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

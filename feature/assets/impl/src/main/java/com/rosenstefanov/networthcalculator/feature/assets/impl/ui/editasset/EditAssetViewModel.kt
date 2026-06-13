package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.core.common.CurrencyFormatter
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
class EditAssetViewModel @Inject constructor(
    private val currencyFormatter: CurrencyFormatter,
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditAssetUiState>(
        EditAssetUiState.Content(
            name = "Primary Residence",
            amount = currencyFormatter.format("312000"),
            description = "Family home in Austin. Primary residence, purchased 2021.",
        ),
    )
    val uiState: StateFlow<EditAssetUiState> = _uiState.asStateFlow()

    private val _effects = Channel<EditAssetEffect>(Channel.BUFFERED)
    val effects: Flow<EditAssetEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: EditAssetIntent) {
        when (intent) {
            is EditAssetIntent.NameChanged -> updateContent { it.copy(name = intent.name) }
            is EditAssetIntent.AmountChanged ->
                updateContent { it.copy(amount = currencyFormatter.format(intent.amount)) }
            is EditAssetIntent.DescriptionChanged ->
                updateContent { it.copy(description = intent.description) }
            EditAssetIntent.SaveClicked -> emitEffect(EditAssetEffect.NavigateBack)
            EditAssetIntent.CloseClicked -> emitEffect(EditAssetEffect.NavigateBack)
        }
    }

    private inline fun updateContent(transform: (EditAssetUiState.Content) -> EditAssetUiState.Content) {
        val current = _uiState.value
        if (current is EditAssetUiState.Content) {
            _uiState.value = transform(current)
        }
    }

    private fun emitEffect(effect: EditAssetEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

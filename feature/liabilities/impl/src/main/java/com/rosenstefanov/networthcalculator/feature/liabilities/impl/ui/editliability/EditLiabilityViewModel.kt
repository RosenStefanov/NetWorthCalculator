package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.core.common.CurrencyFormatter
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
class EditLiabilityViewModel @Inject constructor(
    private val currencyFormatter: CurrencyFormatter,
) : ViewModel() {

    private val _uiState = MutableStateFlow<EditLiabilityUiState>(
        EditLiabilityUiState.Content(
            name = "Mortgage",
            amount = currencyFormatter.format("108200"),
            description = "30-year fixed mortgage on the Austin home, originated 2021.",
        ),
    )
    val uiState: StateFlow<EditLiabilityUiState> = _uiState.asStateFlow()

    private val _effects = Channel<EditLiabilityEffect>(Channel.BUFFERED)
    val effects: Flow<EditLiabilityEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: EditLiabilityIntent) {
        when (intent) {
            is EditLiabilityIntent.NameChanged -> updateContent { it.copy(name = intent.name) }
            is EditLiabilityIntent.AmountChanged ->
                updateContent { it.copy(amount = currencyFormatter.format(intent.amount)) }
            is EditLiabilityIntent.DescriptionChanged ->
                updateContent { it.copy(description = intent.description) }
            EditLiabilityIntent.SaveClicked -> emitEffect(EditLiabilityEffect.NavigateBack)
            EditLiabilityIntent.CloseClicked -> emitEffect(EditLiabilityEffect.NavigateBack)
        }
    }

    private inline fun updateContent(
        transform: (EditLiabilityUiState.Content) -> EditLiabilityUiState.Content,
    ) {
        val current = _uiState.value
        if (current is EditLiabilityUiState.Content) {
            _uiState.value = transform(current)
        }
    }

    private fun emitEffect(effect: EditLiabilityEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }
}

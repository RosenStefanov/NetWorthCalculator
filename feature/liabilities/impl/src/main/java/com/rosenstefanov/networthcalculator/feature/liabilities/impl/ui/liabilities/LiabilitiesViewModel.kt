package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.AllocItem
import com.rosenstefanov.networthcalculator.core.ui.models.Holding
import com.rosenstefanov.networthcalculator.core.ui.models.SortMode
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
            LiabilitiesIntent.HoldingClicked -> emitEffect(LiabilitiesEffect.NavigateToDetail)
            is LiabilitiesIntent.SortSelected -> {
                val current = _uiState.value
                if (current is LiabilitiesUiState.Content) {
                    _uiState.value = current.copy(selectedSort = intent.mode)
                }
            }
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
                allocationTotal = 127_550L,
                allocation = listOf(
                    AllocItem("Property", Color(0xFF7C3AED), 108_200L),
                    AllocItem("Vehicle", Color(0xFF9333EA), 12_300L),
                    AllocItem("Revolving", Color(0xFFA855F7), 4_850L),
                    AllocItem("Education", Color(0xFFC026D3), 2_200L),
                ),
                holdingsTotal = 127_550L,
                selectedSort = SortMode.Largest,
                holdings = listOf(
                    Holding("Mortgage", "Property", 108_200L, Color(0xFF7C3AED), NetWorthIcons.Home),
                    Holding("Auto Loan", "Vehicle", 12_300L, Color(0xFF9333EA), NetWorthIcons.Car),
                    Holding("Credit Cards", "Revolving", 4_850L, Color(0xFFA855F7), NetWorthIcons.LiabilitiesCard),
                    Holding("Student Loan", "Education", 2_200L, Color(0xFFC026D3), NetWorthIcons.Document),
                ),
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

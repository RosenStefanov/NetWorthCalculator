package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.AllocItem
import com.rosenstefanov.networthcalculator.core.ui.models.Holding
import com.rosenstefanov.networthcalculator.core.ui.models.SortMode
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthColors
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsUiState
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
class AssetsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<AssetsUiState>(AssetsUiState.Loading)
    val uiState: StateFlow<AssetsUiState> = _uiState.asStateFlow()

    private val _effects = Channel<AssetsEffect>(Channel.BUFFERED)
    val effects: Flow<AssetsEffect> = _effects.receiveAsFlow()

    init {
        load()
    }

    fun onIntent(intent: AssetsIntent) {
        when (intent) {
            AssetsIntent.SettingsClicked -> emitEffect(AssetsEffect.NavigateToSettings)
            AssetsIntent.AddAccountClicked -> emitEffect(AssetsEffect.NavigateToAddAccount)
            AssetsIntent.HoldingClicked -> emitEffect(AssetsEffect.NavigateToDetail)
            is AssetsIntent.SortSelected -> {
                val current = _uiState.value
                if (current is AssetsUiState.Content) {
                    _uiState.value = current.copy(selectedSort = intent.mode)
                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = AssetsUiState.Loading
            delay(LOAD_DELAY_MS)
            _uiState.value = AssetsUiState.Content(
                total = "$412,300",
                deltaText = "+3.1%",
                isGain = true,
                summary = "7 holdings · 5 categories",
                allocationTotal = 412_300L,
                allocation = listOf(
                    AllocItem("Real Estate", NetWorthColors.Blue, 312_000L),
                    AllocItem("Investments", NetWorthColors.Indigo, 58_400L),
                    AllocItem("Retirement", NetWorthColors.Violet, 24_500L),
                    AllocItem("Cash", NetWorthColors.Purple, 12_400L),
                    AllocItem("Vehicles", NetWorthColors.Grape, 5_000L),
                ),
                holdingsTotal = 412_300L,
                selectedSort = SortMode.Largest,
                holdings = listOf(
                    Holding("Primary Residence", "Real Estate", 312_000L, NetWorthColors.Blue, NetWorthIcons.Home),
                    Holding("Brokerage", "Investments", 46_000L, NetWorthColors.Indigo, NetWorthIcons.ChartUp),
                    Holding("401(k)", "Retirement", 24_500L, NetWorthColors.Violet, NetWorthIcons.AssetsCoins),
                    Holding("Crypto Wallet", "Investments", 12_400L, NetWorthColors.Indigo, NetWorthIcons.AssetsCoins),
                    Holding("Savings", "Cash", 7_400L, NetWorthColors.Purple, NetWorthIcons.Cash),
                    Holding("Checking", "Cash", 5_000L, NetWorthColors.Purple, NetWorthIcons.Cash),
                    Holding("Vehicle", "Vehicles", 5_000L, NetWorthColors.Grape, NetWorthIcons.Car),
                ),
            )
        }
    }

    private fun emitEffect(effect: AssetsEffect) {
        viewModelScope.launch { _effects.send(effect) }
    }

    private companion object {
        const val LOAD_DELAY_MS = 300L
    }
}

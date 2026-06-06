package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardEffect
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardIntent
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardUiState
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.HoldingRow
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.Money
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    private val _effects = Channel<DashboardEffect>(Channel.BUFFERED)
    val effects: Flow<DashboardEffect> = _effects.receiveAsFlow()

    init {
        load()
    }

    fun onIntent(intent: DashboardIntent) {
        when (intent) {
            DashboardIntent.Refresh -> load()
            DashboardIntent.RetryClicked -> load()
            DashboardIntent.SettingsClicked -> viewModelScope.launch {
                _effects.send(DashboardEffect.NavigateToSettings)
            }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading
            delay(LOAD_DELAY_MS)
            _uiState.value = fakeContent()
        }
    }

    // Hardcoded data for Chunk 1. Replaced by a repository-backed flow in Chunk 8.
    private fun fakeContent(): DashboardUiState.Content {
        fun eur(value: String) = Money(BigDecimal(value), "EUR")
        return DashboardUiState.Content(
            netWorth = eur("42500.00"),
            assetsTotal = eur("58200.00"),
            liabilitiesTotal = eur("15700.00"),
            topHoldings = listOf(
                HoldingRow(1, "Apartment", "🏠", eur("250000.00"), isLiability = false),
                HoldingRow(2, "Mortgage", "🏦", eur("200000.00"), isLiability = true),
                HoldingRow(3, "S&P 500 ETF", "📈", eur("30000.00"), isLiability = false),
                HoldingRow(4, "Savings (USD)", "💰", eur("12100.00"), isLiability = false),
                HoldingRow(5, "Credit Card", "💳", eur("1500.00"), isLiability = true),
            ),
        )
    }

    private companion object {
        const val LOAD_DELAY_MS = 300L
    }
}

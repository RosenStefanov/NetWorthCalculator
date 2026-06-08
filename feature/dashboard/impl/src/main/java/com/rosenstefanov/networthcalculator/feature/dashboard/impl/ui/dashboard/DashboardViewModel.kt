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
            is DashboardIntent.RangeSelected -> {
                val current = _uiState.value
                if (current is DashboardUiState.Content) {
                    _uiState.value = current.copy(selectedRange = intent.range)
                }
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

    private fun fakeContent(): DashboardUiState.Content {
        fun eur(value: String) = Money(BigDecimal(value), "EUR")
        val assets = eur("58200.00")
        val liabilities = eur("15700.00")
        return DashboardUiState.Content(
            netWorth = eur("42500.00"),
            assetsTotal = assets,
            liabilitiesTotal = liabilities,
            assetsWeight = assetsWeight(assets, liabilities),
            trend = DashboardUiState.NetWorthTrend(
                assets = SAMPLE_ASSETS_TREND,
                liabilities = SAMPLE_LIABILITIES_TREND,
                monthLabels = SAMPLE_MONTH_LABELS,
            ),
            ranges = RANGES,
            selectedRange = DEFAULT_RANGE,
            change = DashboardUiState.NetWorthChange(
                label = "+€1,480 · 4.2% this month",
                isGain = true,
            ),
            topHoldings = listOf(
                HoldingRow(1, "Apartment", "🏠", eur("250000.00"), isLiability = false),
                HoldingRow(2, "Mortgage", "🏦", eur("200000.00"), isLiability = true),
                HoldingRow(3, "S&P 500 ETF", "📈", eur("30000.00"), isLiability = false),
                HoldingRow(4, "Savings (USD)", "💰", eur("12100.00"), isLiability = false),
                HoldingRow(5, "Credit Card", "💳", eur("1500.00"), isLiability = true),
            ),
        )
    }

    private fun assetsWeight(assets: Money, liabilities: Money): Float {
        val total = assets.amount + liabilities.amount
        if (total <= BigDecimal.ZERO) return 1f
        return assets.amount.divide(total, 4, java.math.RoundingMode.HALF_UP).toFloat()
    }

    private companion object {
        const val LOAD_DELAY_MS = 300L
        const val DEFAULT_RANGE = "1Y"
        val RANGES = listOf("1M", "6M", "1Y", "All")

        val SAMPLE_ASSETS_TREND =
            listOf(.321f, .306f, .292f, .297f, .275f, .257f, .262f, .243f, .228f, .221f, .206f, .194f)
        val SAMPLE_LIABILITIES_TREND =
            listOf(.858f, .861f, .866f, .863f, .870f, .873f, .875f, .880f, .883f, .885f, .887f, .889f)
        val SAMPLE_MONTH_LABELS = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun")
    }
}

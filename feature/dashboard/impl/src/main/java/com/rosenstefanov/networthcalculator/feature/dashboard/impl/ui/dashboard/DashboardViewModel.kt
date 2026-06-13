package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rosenstefanov.networthcalculator.core.ui.models.HoldingType
import com.rosenstefanov.networthcalculator.core.ui.models.TopHolding
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardEffect
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardIntent
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardUiState
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
            is DashboardIntent.HoldingTypeSelected -> {
                val current = _uiState.value
                if (current is DashboardUiState.Content) {
                    _uiState.value = current.copy(selectedHoldingType = intent.type)
                }
            }
            is DashboardIntent.HoldingClicked -> viewModelScope.launch {
                val effect = when (intent.type) {
                    HoldingType.Assets -> DashboardEffect.NavigateToAssetDetail
                    HoldingType.Liabilities -> DashboardEffect.NavigateToLiabilityDetail
                }
                _effects.send(effect)
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
            assetHoldings = ASSET_HOLDINGS,
            liabilityHoldings = LIABILITY_HOLDINGS,
            selectedHoldingType = HoldingType.Assets,
            change = DashboardUiState.NetWorthChange(
                label = "+€1,480 · 4.2% this month",
                isGain = true,
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
            listOf(48_000f, 49_500f, 50_800f, 51_600f, 52_900f, 53_700f, 54_800f, 55_500f, 56_400f, 57_100f, 57_700f, 58_200f)
        val SAMPLE_LIABILITIES_TREND =
            listOf(15_000f, 15_100f, 15_200f, 15_300f, 15_400f, 15_450f, 15_500f, 15_550f, 15_600f, 15_650f, 15_680f, 15_700f)
        val SAMPLE_MONTH_LABELS = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun")

        val ASSET_HOLDINGS = listOf(
            TopHolding(NetWorthIcons.Home, "Primary Residence", "Real estate", "$312,000", 312_000),
            TopHolding(NetWorthIcons.ChartUp, "Brokerage", "Investments", "$58,400", 58_400),
            TopHolding(NetWorthIcons.AssetsCoins, "401(k)", "Retirement", "$24,500", 24_500),
            TopHolding(NetWorthIcons.Cash, "Cash & Savings", "Bank", "$12,400", 12_400),
            TopHolding(NetWorthIcons.Car, "Vehicle", "Auto", "$5,000", 5_000),
        )
        val LIABILITY_HOLDINGS = listOf(
            TopHolding(NetWorthIcons.Home, "Mortgage", "Home loan", "$108,200", 108_200),
            TopHolding(NetWorthIcons.Car, "Auto Loan", "Vehicle", "$12,300", 12_300),
            TopHolding(NetWorthIcons.LiabilitiesCard, "Credit Cards", "Revolving", "$4,850", 4_850),
            TopHolding(NetWorthIcons.Document, "Student Loan", "Education", "$2,200", 2_200),
        )
    }
}

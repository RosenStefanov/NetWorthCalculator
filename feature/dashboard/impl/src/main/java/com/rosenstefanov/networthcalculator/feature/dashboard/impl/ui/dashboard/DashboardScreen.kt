package com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.AssetsVsLiabilitiesCard
import com.rosenstefanov.networthcalculator.core.ui.component.compactNumber
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthCard
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthDeltaPill
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthTopAppBar
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthTopBarIconButton
import com.rosenstefanov.networthcalculator.core.ui.models.HoldingType
import com.rosenstefanov.networthcalculator.core.ui.models.TopHolding
import com.rosenstefanov.networthcalculator.core.ui.component.TopHoldingsCard
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardEffect
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardIntent
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardUiState
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.Money
import java.math.BigDecimal

@Composable
internal fun DashboardScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAssetDetail: () -> Unit = {},
    onNavigateToLiabilityDetail: () -> Unit = {},
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                DashboardEffect.NavigateToSettings -> onNavigateToSettings()
                DashboardEffect.NavigateToAssetDetail -> onNavigateToAssetDetail()
                DashboardEffect.NavigateToLiabilityDetail -> onNavigateToLiabilityDetail()
            }
        }
    }

    DashboardScreen(
        uiState = uiState,
        onIntent = viewModel::onIntent,
    )
}

@Composable
internal fun DashboardScreen(
    uiState: DashboardUiState,
    onIntent: (DashboardIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            NetWorthTopAppBar(
                leading = {
                    Column {
                        Text(
                            text = "Hello",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "User",
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                },
                actions = {
                    NetWorthTopBarIconButton(
                        icon = NetWorthIcons.SettingsGear,
                        contentDescription = "Settings",
                        onClick = { onIntent(DashboardIntent.SettingsClicked) },
                    )
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            DashboardUiState.Loading -> DashboardLoading(Modifier.padding(padding))
            DashboardUiState.Empty -> DashboardEmpty(Modifier.padding(padding))
            is DashboardUiState.Content -> DashboardContent(
                state = uiState,
                onIntent = onIntent,
                modifier = Modifier.padding(padding),
            )
            is DashboardUiState.Error -> DashboardError(
                message = uiState.message,
                onRetry = { onIntent(DashboardIntent.RetryClicked) },
                modifier = Modifier.padding(padding),
            )
        }
    }
}

@Composable
private fun DashboardContent(
    state: DashboardUiState.Content,
    onIntent: (DashboardIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 22.dp, end = 22.dp, top = 16.dp, bottom = 96.dp),
    ) {
        NetWorthCard(
            title = "TOTAL NET WORTH",
            modifier = Modifier.padding(bottom = 16.dp),
        ) {
            Text(
                text = state.netWorth.formatted(),
                style = MaterialTheme.typography.displayLarge.copy(letterSpacing = (-0.02).em),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            state.change?.let { change ->
                Spacer(Modifier.height(13.dp))
                NetWorthDeltaPill(text = change.label, isGain = change.isGain)
            }
        }

        AssetsVsLiabilitiesCard(
            assetsValue = state.assetsTotal.formatted(),
            liabilitiesValue = state.liabilitiesTotal.formatted(),
            assetsWeight = state.assetsWeight,
            assetsTrend = state.trend.assets,
            liabilitiesTrend = state.trend.liabilities,
            monthLabels = state.trend.monthLabels,
            ranges = state.ranges,
            selectedRange = state.selectedRange,
            onRangeSelected = { onIntent(DashboardIntent.RangeSelected(it)) },
            formatAxisLabel = { value -> "€" + compactNumber(value) },
        )

        TopHoldingsCard(
            assets = state.assetHoldings,
            liabilities = state.liabilityHoldings,
            selectedType = state.selectedHoldingType,
            onTypeSelected = { onIntent(DashboardIntent.HoldingTypeSelected(it)) },
            modifier = Modifier.padding(top = 16.dp),
            onHoldingClick = { type, _ -> onIntent(DashboardIntent.HoldingClicked(type)) },
        )
    }
}

@Composable
private fun DashboardLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DashboardEmpty(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "No holdings yet",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Add your first asset to start tracking your net worth.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
        )
        Button(onClick = {}) {
            Text("Add your first asset")
        }
    }
}

@Composable
private fun DashboardError(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Something went wrong",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp),
        )
        OutlinedButton(onClick = onRetry) {
            Text("Retry")
        }
    }
}

private fun previewContent() = DashboardUiState.Content(
    netWorth = Money(BigDecimal("42500.00"), "EUR"),
    assetsTotal = Money(BigDecimal("58200.00"), "EUR"),
    liabilitiesTotal = Money(BigDecimal("15700.00"), "EUR"),
    assetsWeight = 0.787f,
    trend = DashboardUiState.NetWorthTrend(
        assets = listOf(48_000f, 49_500f, 50_800f, 51_600f, 52_900f, 53_700f, 54_800f, 55_500f, 56_400f, 57_100f, 57_700f, 58_200f),
        liabilities = listOf(15_000f, 15_100f, 15_200f, 15_300f, 15_400f, 15_450f, 15_500f, 15_550f, 15_600f, 15_650f, 15_680f, 15_700f),
        monthLabels = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun"),
    ),
    ranges = listOf("1M", "6M", "1Y", "All"),
    selectedRange = "1Y",
    change = DashboardUiState.NetWorthChange("+€1,480 · 4.2% this month", isGain = true),
    assetHoldings = listOf(
        TopHolding(NetWorthIcons.Home, "Primary Residence", "Real estate", "$312,000", 312_000),
        TopHolding(NetWorthIcons.ChartUp, "Brokerage", "Investments", "$58,400", 58_400),
        TopHolding(NetWorthIcons.AssetsCoins, "401(k)", "Retirement", "$24,500", 24_500),
        TopHolding(NetWorthIcons.Cash, "Cash & Savings", "Bank", "$12,400", 12_400),
        TopHolding(NetWorthIcons.Car, "Vehicle", "Auto", "$5,000", 5_000),
    ),
    liabilityHoldings = listOf(
        TopHolding(NetWorthIcons.Home, "Mortgage", "Home loan", "$108,200", 108_200),
        TopHolding(NetWorthIcons.Car, "Auto Loan", "Vehicle", "$12,300", 12_300),
        TopHolding(NetWorthIcons.LiabilitiesCard, "Credit Cards", "Revolving", "$4,850", 4_850),
        TopHolding(NetWorthIcons.Document, "Student Loan", "Education", "$2,200", 2_200),
    ),
    selectedHoldingType = HoldingType.Assets,
)

@Preview(showBackground = true)
@Composable
internal fun DashboardLoadingPreview() {
    NetWorthCalculatorTheme {
        DashboardScreen(uiState = DashboardUiState.Loading, onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun DashboardEmptyPreview() {
    NetWorthCalculatorTheme {
        DashboardScreen(uiState = DashboardUiState.Empty, onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun DashboardContentPreview() {
    NetWorthCalculatorTheme {
        DashboardScreen(uiState = previewContent(), onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun DashboardErrorPreview() {
    NetWorthCalculatorTheme {
        DashboardScreen(
            uiState = DashboardUiState.Error("Couldn't load exchange rates"),
            onIntent = {},
        )
    }
}

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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.AssetsVsLiabilitiesCard
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthCard
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthDeltaPill
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthTopAppBar
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardEffect
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardIntent
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardUiState
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.HoldingRow
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.Money
import java.math.BigDecimal

@Composable
internal fun DashboardScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                DashboardEffect.NavigateToSettings -> onNavigateToSettings()
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
                actionIcon = NetWorthIcons.SettingsGear,
                actionContentDescription = "Settings",
                onActionClick = { onIntent(DashboardIntent.SettingsClicked) },
                leading = {
                    Column{
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
            .padding(horizontal = 22.dp, vertical = 16.dp),
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
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Top holdings",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
            )
            TextButton(onClick = {}) {
                Text("See all")
            }
        }

        Spacer(Modifier.height(4.dp))

        state.topHoldings.forEach { holding ->
            HoldingItem(holding)
        }
    }
}

@Composable
private fun HoldingItem(holding: HoldingRow) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = holding.emoji, style = MaterialTheme.typography.titleLarge)
        Text(
            text = holding.label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp),
        )
        val prefix = if (holding.isLiability) "−" else ""
        Text(
            text = "$prefix${holding.amount.formatted()}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = if (holding.isLiability) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onSurface
            },
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
        assets = listOf(.321f, .306f, .292f, .297f, .275f, .257f, .262f, .243f, .228f, .221f, .206f, .194f),
        liabilities = listOf(.858f, .861f, .866f, .863f, .870f, .873f, .875f, .880f, .883f, .885f, .887f, .889f),
        monthLabels = listOf("Jul", "Sep", "Nov", "Jan", "Mar", "Jun"),
    ),
    ranges = listOf("1M", "6M", "1Y", "All"),
    selectedRange = "1Y",
    change = DashboardUiState.NetWorthChange("+€1,480 · 4.2% this month", isGain = true),
    topHoldings = listOf(
        HoldingRow(1, "Apartment", "🏠", Money(BigDecimal("250000.00"), "EUR"), false),
        HoldingRow(2, "Mortgage", "🏦", Money(BigDecimal("200000.00"), "EUR"), true),
        HoldingRow(3, "S&P 500 ETF", "📈", Money(BigDecimal("30000.00"), "EUR"), false),
        HoldingRow(4, "Savings (USD)", "💰", Money(BigDecimal("12100.00"), "EUR"), false),
        HoldingRow(5, "Credit Card", "💳", Money(BigDecimal("1500.00"), "EUR"), true),
    ),
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

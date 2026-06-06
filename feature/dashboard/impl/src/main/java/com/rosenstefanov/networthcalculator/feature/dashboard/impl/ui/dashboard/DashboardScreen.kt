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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardEffect
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardIntent
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.DashboardUiState
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.HoldingRow
import com.rosenstefanov.networthcalculator.feature.dashboard.impl.ui.dashboard.models.Money
import java.math.BigDecimal

/**
 * Route-level composable. Owns the ViewModel, collects state + one-shot effects,
 * and delegates rendering to the stateless [DashboardScreen] below.
 */
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

/**
 * Stateless screen — renders purely from [uiState]. Drives all previews + snapshots.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DashboardScreen(
    uiState: DashboardUiState,
    onIntent: (DashboardIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Net Worth Calculator") },
                actions = {
                    IconButton(onClick = { onIntent(DashboardIntent.SettingsClicked) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
            )
        },
    ) { padding ->
        when (uiState) {
            DashboardUiState.Loading -> DashboardLoading(Modifier.padding(padding))
            DashboardUiState.Empty -> DashboardEmpty(Modifier.padding(padding))
            is DashboardUiState.Content -> DashboardContent(
                state = uiState,
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

// region State composables

@Composable
private fun DashboardContent(
    state: DashboardUiState.Content,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        // Hero
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Net Worth",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = state.netWorth.formatted(),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(Modifier.height(16.dp))

        // Two summary tiles
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            SummaryTile(
                label = "Assets",
                value = state.assetsTotal.formatted(),
                valueColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f),
            )
            SummaryTile(
                label = "Liabilities",
                value = state.liabilitiesTotal.formatted(),
                valueColor = MaterialTheme.colorScheme.error,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(Modifier.height(24.dp))

        // Top holdings header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Top holdings",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f),
            )
            TextButton(onClick = { /* See all — wired in Chunk 9 */ }) {
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
private fun SummaryTile(
    label: String,
    value: String,
    valueColor: Color,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = valueColor,
            )
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
        Button(onClick = { /* Add asset — wired in Chunk 9 */ }) {
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

// endregion

// region Previews

private fun previewContent() = DashboardUiState.Content(
    netWorth = Money(BigDecimal("42500.00"), "EUR"),
    assetsTotal = Money(BigDecimal("58200.00"), "EUR"),
    liabilitiesTotal = Money(BigDecimal("15700.00"), "EUR"),
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
    MaterialTheme {
        DashboardScreen(uiState = DashboardUiState.Loading, onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun DashboardEmptyPreview() {
    MaterialTheme {
        DashboardScreen(uiState = DashboardUiState.Empty, onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun DashboardContentPreview() {
    MaterialTheme {
        DashboardScreen(uiState = previewContent(), onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun DashboardErrorPreview() {
    MaterialTheme {
        DashboardScreen(
            uiState = DashboardUiState.Error("Couldn't load exchange rates"),
            onIntent = {},
        )
    }
}

// endregion

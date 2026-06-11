package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.AllocationCard
import com.rosenstefanov.networthcalculator.core.ui.component.RankedListSection
import com.rosenstefanov.networthcalculator.core.ui.component.SectionTopBar
import com.rosenstefanov.networthcalculator.core.ui.component.TotalCard
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.models.AllocItem
import com.rosenstefanov.networthcalculator.core.ui.models.Holding
import com.rosenstefanov.networthcalculator.core.ui.models.SortMode
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthBrandBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets.models.AssetsUiState

@Composable
internal fun AssetsScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAddAccount: () -> Unit,
    viewModel: AssetsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                AssetsEffect.NavigateToSettings -> onNavigateToSettings()
                AssetsEffect.NavigateToAddAccount -> onNavigateToAddAccount()
            }
        }
    }

    AssetsScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun AssetsScreen(
    uiState: AssetsUiState,
    onIntent: (AssetsIntent) -> Unit,
) {
    val accentGradient = NetWorthBrandBrush
    val accentGlow = Color(0xFF5B45F5).copy(alpha = 0.6f)

    Scaffold(
        topBar = {
            SectionTopBar(
                title = "Assets",
                addBrush = accentGradient,
                addGlow = accentGlow,
                onSettings = { onIntent(AssetsIntent.SettingsClicked) },
                onAdd = { onIntent(AssetsIntent.AddAccountClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            AssetsUiState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }

            is AssetsUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 22.dp, vertical = 16.dp),
            ) {
                TotalCard(
                    label = "Total Assets",
                    value = uiState.total,
                    deltaText = uiState.deltaText,
                    isGain = uiState.isGain,
                    count = uiState.summary,
                    gradient = accentGradient,
                    glow = accentGlow,
                    modifier = Modifier.padding(bottom = 18.dp),
                )
                AllocationCard(
                    items = uiState.allocation,
                    total = uiState.allocationTotal,
                    modifier = Modifier.padding(bottom = 18.dp),
                )
                RankedListSection(
                    title = "All assets",
                    holdings = uiState.holdings,
                    total = uiState.holdingsTotal,
                    sort = uiState.selectedSort,
                    onSort = { onIntent(AssetsIntent.SortSelected(it)) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AssetsScreenContentPreview() {
    NetWorthCalculatorTheme {
        AssetsScreen(
            uiState = AssetsUiState.Content(
                total = "$412,300",
                deltaText = "+3.1%",
                isGain = true,
                summary = "7 holdings · 5 categories",
                allocationTotal = 412_300L,
                allocation = listOf(
                    AllocItem("Real Estate", Color(0xFF3B6BFF), 312_000L),
                    AllocItem("Investments", Color(0xFF5B45F5), 58_400L),
                    AllocItem("Retirement", Color(0xFF7C3AED), 24_500L),
                    AllocItem("Cash", Color(0xFF8A2EE8), 12_400L),
                    AllocItem("Vehicles", Color(0xFF9333EA), 5_000L),
                ),
                holdingsTotal = 412_300L,
                selectedSort = SortMode.Largest,
                holdings = listOf(
                    Holding("Primary Residence", "Real Estate", 312_000L, Color(0xFF3B6BFF), NetWorthIcons.Home),
                    Holding("Brokerage", "Investments", 46_000L, Color(0xFF5B45F5), NetWorthIcons.ChartUp),
                    Holding("401(k)", "Retirement", 24_500L, Color(0xFF7C3AED), NetWorthIcons.AssetsCoins),
                    Holding("Savings", "Cash", 7_400L, Color(0xFF8A2EE8), NetWorthIcons.Cash),
                ),
            ),
            onIntent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun AssetsScreenLoadingPreview() {
    NetWorthCalculatorTheme {
        AssetsScreen(uiState = AssetsUiState.Loading, onIntent = {})
    }
}

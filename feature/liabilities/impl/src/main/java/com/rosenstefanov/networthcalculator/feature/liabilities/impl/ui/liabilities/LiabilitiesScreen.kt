package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities

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
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesUiState

@Composable
internal fun LiabilitiesScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAddAccount: () -> Unit,
    onNavigateToHoldingDetail: () -> Unit = {},
    viewModel: LiabilitiesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LiabilitiesEffect.NavigateToSettings -> onNavigateToSettings()
                LiabilitiesEffect.NavigateToAddAccount -> onNavigateToAddAccount()
                LiabilitiesEffect.NavigateToDetail -> onNavigateToHoldingDetail()
            }
        }
    }

    LiabilitiesScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun LiabilitiesScreen(
    uiState: LiabilitiesUiState,
    onIntent: (LiabilitiesIntent) -> Unit,
) {
    val accentGradient = NetWorthLiabilitiesBrush
    val accentGlow = Color(0xFF9333EA).copy(alpha = 0.6f)

    Scaffold(
        topBar = {
            SectionTopBar(
                title = "Liabilities",
                addBrush = accentGradient,
                addGlow = accentGlow,
                onSettings = { onIntent(LiabilitiesIntent.SettingsClicked) },
                onAdd = { onIntent(LiabilitiesIntent.AddAccountClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            LiabilitiesUiState.Loading -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }

            is LiabilitiesUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(start = 22.dp, end = 22.dp, top = 16.dp, bottom = 104.dp),
            ) {
                TotalCard(
                    label = "Total Liabilities",
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
                    title = "All liabilities",
                    holdings = uiState.holdings,
                    total = uiState.holdingsTotal,
                    sort = uiState.selectedSort,
                    onSort = { onIntent(LiabilitiesIntent.SortSelected(it)) },
                    onHoldingClick = { onIntent(LiabilitiesIntent.HoldingClicked) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun LiabilitiesScreenContentPreview() {
    NetWorthCalculatorTheme {
        LiabilitiesScreen(
            uiState = LiabilitiesUiState.Content(
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
            ),
            onIntent = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun LiabilitiesScreenLoadingPreview() {
    NetWorthCalculatorTheme {
        LiabilitiesScreen(uiState = LiabilitiesUiState.Loading, onIntent = {})
    }
}

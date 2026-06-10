package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.SectionTopBar
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilities.models.LiabilitiesUiState

@Composable
internal fun LiabilitiesScreen(
    onNavigateToSettings: () -> Unit,
    onNavigateToAddAccount: () -> Unit,
    viewModel: LiabilitiesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LiabilitiesEffect.NavigateToSettings -> onNavigateToSettings()
                LiabilitiesEffect.NavigateToAddAccount -> onNavigateToAddAccount()
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
    Scaffold(
        topBar = {
            SectionTopBar(
                title = "Liabilities",
                addBrush = NetWorthLiabilitiesBrush,
                addGlow = Color(0xFF9333EA).copy(alpha = 0.6f),
                onSettings = { onIntent(LiabilitiesIntent.SettingsClicked) },
                onAdd = { onIntent(LiabilitiesIntent.AddAccountClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center,
        ) {
            when (uiState) {
                LiabilitiesUiState.Loading -> CircularProgressIndicator()
                LiabilitiesUiState.Content -> Text("Liabilities")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun LiabilitiesScreenContentPreview() {
    NetWorthCalculatorTheme {
        LiabilitiesScreen(uiState = LiabilitiesUiState.Content, onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun LiabilitiesScreenLoadingPreview() {
    NetWorthCalculatorTheme {
        LiabilitiesScreen(uiState = LiabilitiesUiState.Loading, onIntent = {})
    }
}

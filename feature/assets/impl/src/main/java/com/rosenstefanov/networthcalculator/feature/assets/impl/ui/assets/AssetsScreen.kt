package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assets

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
    Scaffold(
        topBar = {
            SectionTopBar(
                title = "Assets",
                addBrush = NetWorthBrandBrush,
                addGlow = Color(0xFF5B45F5).copy(alpha = 0.6f),
                onSettings = { onIntent(AssetsIntent.SettingsClicked) },
                onAdd = { onIntent(AssetsIntent.AddAccountClicked) },
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
                AssetsUiState.Loading -> CircularProgressIndicator()
                AssetsUiState.Content -> Text("Assets")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AssetsScreenContentPreview() {
    NetWorthCalculatorTheme {
        AssetsScreen(uiState = AssetsUiState.Content, onIntent = {})
    }
}

@Preview(showBackground = true)
@Composable
internal fun AssetsScreenLoadingPreview() {
    NetWorthCalculatorTheme {
        AssetsScreen(uiState = AssetsUiState.Loading, onIntent = {})
    }
}

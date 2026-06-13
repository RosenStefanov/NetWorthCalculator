package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.DetailTopBar
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.assetdetail.models.AssetDetailUiState

@Composable
internal fun AssetDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: AssetDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                AssetDetailEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    AssetDetailScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun AssetDetailScreen(
    uiState: AssetDetailUiState,
    onIntent: (AssetDetailIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Asset Details",
                onNavigateBack = { onIntent(AssetDetailIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            AssetDetailUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 22.dp),
            ) {
                Text(
                    text = "Asset details coming soon.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AssetDetailScreenPreview() {
    NetWorthCalculatorTheme {
        AssetDetailScreen(uiState = AssetDetailUiState.Content, onIntent = {})
    }
}

package com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset

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
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetEffect
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetIntent
import com.rosenstefanov.networthcalculator.feature.assets.impl.ui.editasset.models.EditAssetUiState

@Composable
internal fun EditAssetScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditAssetViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                EditAssetEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    EditAssetScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun EditAssetScreen(
    uiState: EditAssetUiState,
    onIntent: (EditAssetIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Edit Asset",
                onNavigateBack = { onIntent(EditAssetIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            EditAssetUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 22.dp),
            ) {
                Text(
                    text = "Edit asset form coming soon.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun EditAssetScreenPreview() {
    NetWorthCalculatorTheme {
        EditAssetScreen(uiState = EditAssetUiState.Content, onIntent = {})
    }
}

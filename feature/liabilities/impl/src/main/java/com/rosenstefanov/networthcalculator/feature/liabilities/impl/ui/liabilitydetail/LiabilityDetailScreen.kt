package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail

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
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.liabilitydetail.models.LiabilityDetailUiState

@Composable
internal fun LiabilityDetailScreen(
    onNavigateBack: () -> Unit,
    viewModel: LiabilityDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                LiabilityDetailEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    LiabilityDetailScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun LiabilityDetailScreen(
    uiState: LiabilityDetailUiState,
    onIntent: (LiabilityDetailIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Liability Details",
                onNavigateBack = { onIntent(LiabilityDetailIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            LiabilityDetailUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 22.dp),
            ) {
                Text(
                    text = "Liability details coming soon.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun LiabilityDetailScreenPreview() {
    NetWorthCalculatorTheme {
        LiabilityDetailScreen(uiState = LiabilityDetailUiState.Content, onIntent = {})
    }
}

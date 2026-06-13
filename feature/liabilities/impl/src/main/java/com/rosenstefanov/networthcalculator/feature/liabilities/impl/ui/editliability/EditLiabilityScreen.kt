package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability

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
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.editliability.models.EditLiabilityUiState

@Composable
internal fun EditLiabilityScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditLiabilityViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                EditLiabilityEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    EditLiabilityScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun EditLiabilityScreen(
    uiState: EditLiabilityUiState,
    onIntent: (EditLiabilityIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            DetailTopBar(
                title = "Edit Liability",
                onNavigateBack = { onIntent(EditLiabilityIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            EditLiabilityUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 22.dp),
            ) {
                Text(
                    text = "Edit liability form coming soon.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun EditLiabilityScreenPreview() {
    NetWorthCalculatorTheme {
        EditLiabilityScreen(uiState = EditLiabilityUiState.Content, onIntent = {})
    }
}

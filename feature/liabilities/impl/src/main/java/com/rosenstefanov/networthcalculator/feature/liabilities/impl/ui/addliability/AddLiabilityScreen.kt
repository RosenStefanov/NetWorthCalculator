package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.rosenstefanov.networthcalculator.core.ui.component.NetWorthTopAppBar
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityEffect
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityIntent
import com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability.models.AddLiabilityUiState

@Composable
internal fun AddLiabilityScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddLiabilityViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                AddLiabilityEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    AddLiabilityScreen(uiState = uiState, onIntent = viewModel::onIntent)
}

@Composable
internal fun AddLiabilityScreen(
    uiState: AddLiabilityUiState,
    onIntent: (AddLiabilityIntent) -> Unit,
) {
    Scaffold(
        topBar = {
            NetWorthTopAppBar(
                horizontalPadding = 22.dp,
                leading = {
                    Text("Add Liability", style = MaterialTheme.typography.headlineSmall)
                },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            AddLiabilityUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = "Liability form coming soon.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Button(onClick = { onIntent(AddLiabilityIntent.SaveClicked) }) {
                    Text("Save liability")
                }
                OutlinedButton(onClick = { onIntent(AddLiabilityIntent.CloseClicked) }) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AddLiabilityScreenPreview() {
    NetWorthCalculatorTheme {
        AddLiabilityScreen(uiState = AddLiabilityUiState.Content, onIntent = {})
    }
}

package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.DetailTopBar
import com.rosenstefanov.networthcalculator.core.ui.component.InputField
import com.rosenstefanov.networthcalculator.core.ui.component.KindBanner
import com.rosenstefanov.networthcalculator.core.ui.icon.NetWorthIcons
import com.rosenstefanov.networthcalculator.core.ui.theme.JakartaSans
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthCalculatorTheme
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthLiabilitiesBrush
import com.rosenstefanov.networthcalculator.core.ui.theme.NetWorthTheme
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
            DetailTopBar(
                title = "Add Liability",
                onNavigateBack = { onIntent(AddLiabilityIntent.CloseClicked) },
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->
        when (uiState) {
            is AddLiabilityUiState.Content -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 22.dp),
            ) {
                KindBanner(
                    title = "New liability",
                    subtitle = "Something you owe",
                    icon = NetWorthIcons.Scale,
                    gradient = NetWorthLiabilitiesBrush,
                    glow = Color(0xFF9333EA).copy(alpha = 0.6f),
                )
                Text(
                    text = "Name",
                    fontFamily = JakartaSans,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NetWorthTheme.extendedColors.inkSub,
                    modifier = Modifier.padding(top = 18.dp, bottom = 7.dp),
                )
                InputField(
                    value = uiState.name,
                    placeholder = "e.g. Mortgage, Card…",
                    accentColor = Color(0xFF9333EA),
                    onValueChange = { onIntent(AddLiabilityIntent.NameChanged(it)) },
                )
                Spacer(Modifier.height(18.dp))
                Button(onClick = { onIntent(AddLiabilityIntent.SaveClicked) }) {
                    Text("Save liability")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AddLiabilityScreenPreview() {
    NetWorthCalculatorTheme {
        AddLiabilityScreen(uiState = AddLiabilityUiState.Content(name = "Mortgage"), onIntent = {})
    }
}

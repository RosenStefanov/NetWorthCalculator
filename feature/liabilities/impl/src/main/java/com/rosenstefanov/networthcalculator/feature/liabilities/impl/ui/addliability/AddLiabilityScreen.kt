package com.rosenstefanov.networthcalculator.feature.liabilities.impl.ui.addliability

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rosenstefanov.networthcalculator.core.ui.component.CategorySection
import com.rosenstefanov.networthcalculator.core.ui.component.DescriptionField
import com.rosenstefanov.networthcalculator.core.ui.component.DetailTopBar
import com.rosenstefanov.networthcalculator.core.ui.component.InputField
import com.rosenstefanov.networthcalculator.core.ui.component.KindBanner
import com.rosenstefanov.networthcalculator.core.ui.component.PrimaryCtaBar
import com.rosenstefanov.networthcalculator.core.ui.models.LiabilityCategories
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
            is AddLiabilityUiState.Content -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = padding.calculateTopPadding()),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 22.dp)
                        .padding(bottom = 120.dp),
                ) {
                    KindBanner(
                    title = uiState.name.ifBlank { "New liability" },
                    subtitle = uiState.category.name,
                    icon = uiState.category.icon,
                    amount = "$" + uiState.amount.ifEmpty { "0" },
                    amountLabel = "OWED",
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
                Text(
                    text = "Amount owed",
                    fontFamily = JakartaSans,
                    fontSize = 12.5.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NetWorthTheme.extendedColors.inkSub,
                    modifier = Modifier.padding(top = 18.dp, bottom = 7.dp),
                )
                InputField(
                    value = uiState.amount,
                    placeholder = "0",
                    accentColor = Color(0xFF9333EA),
                    onValueChange = { onIntent(AddLiabilityIntent.AmountChanged(it)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                CategorySection(
                    categories = LiabilityCategories,
                    selected = uiState.category,
                    accent = Color(0xFF9333EA),
                    onSelect = { onIntent(AddLiabilityIntent.CategorySelected(it)) },
                    modifier = Modifier.padding(top = 18.dp),
                )
                DescriptionField(
                    value = uiState.description,
                    placeholder = "Add a note — lender, rate, due date, etc.",
                    accentColor = Color(0xFF9333EA),
                    onValueChange = { onIntent(AddLiabilityIntent.DescriptionChanged(it)) },
                    modifier = Modifier.padding(top = 18.dp),
                )
                }

                val formValid = uiState.name.isNotBlank() && uiState.amount.isNotBlank()

                PrimaryCtaBar(
                    label = "Add liability",
                    gradient = NetWorthLiabilitiesBrush,
                    glow = Color(0xFF9333EA).copy(alpha = 0.55f),
                    enabled = formValid,
                    onClick = { onIntent(AddLiabilityIntent.SaveClicked) },
                    modifier = Modifier.align(Alignment.BottomCenter),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
internal fun AddLiabilityScreenPreview() {
    NetWorthCalculatorTheme {
        AddLiabilityScreen(
            uiState = AddLiabilityUiState.Content(name = "Mortgage", amount = "12,300"),
            onIntent = {},
        )
    }
}
